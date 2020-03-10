package com.application.base.service.user.impl;

import com.application.base.entity.User;
import com.application.base.entity.UserInfo;
import com.application.base.exception.business.BusinessException;
import com.application.base.exception.technical.AuthenticationException;
import com.application.base.exception.technical.DaoException;
import com.application.base.repository.UserRepository;
import com.application.base.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    private UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void saveUser(User user, boolean isCreation) throws DaoException, BusinessException {
        try {
            if (user != null) {
                User existedUser = userRepository.getOneByEmail(user.getEmail());
                if (existedUser == null && isCreation) {
                    log.info("New user created in database : {}", user.getEmail());
                    userRepository.saveAndFlush(user);
                } else if(existedUser != null && !isCreation){
                    log.info("Updating existing user");
                    updateUserField(existedUser.getEmail(), user.getUserInfo(), existedUser.getUserInfo());
                    userRepository.saveAndFlush(existedUser);
                } else if(existedUser != null){
                    throw new BusinessException("User " + user.getEmail() + " already exists.");
                } else {
                    throw new BusinessException("User " + user.getEmail() + " does not exist and cannot be updated.");
                }
            }
        } catch (JpaSystemException e){
            log.error("Error while saving user {}", user.getEmail(), e);
            throw new DaoException("Error while saving user " + user.getEmail());
        }

    }

    @Override
    public List<User> getAll() throws DaoException {
        try {
            return userRepository.findAll();
        } catch (Exception e){
            log.error("Error while getting all users", e);
            throw new DaoException("Error while getting all users.");
        }
    }

    @Override
    public User getByEmail(String email) throws DaoException {
        try {
            return userRepository.getOneByEmail(email);
        } catch (Exception e){
            log.error("Error while getting user by email : {}", email, e);
            throw new DaoException("Error while getting user by email : "+ email);
        }
    }

    @Override
    public void updatePassword(String email, String lastPassword, String newPassword) throws AuthenticationException {
        User user = userRepository.getOneByEmail(email);
        String encodeLastPassword = encoder.encode(lastPassword);
        if(user.getPassword().equals(encodeLastPassword)){
            String encodedNewPassword = encoder.encode(newPassword);
            userRepository.savePassword(email, encodedNewPassword);
        } else {
            throw new AuthenticationException("Last password incorrect");
        }

    }

    private void updateUserField(String email, UserInfo newUser, UserInfo existingUser){
        boolean updated = false;

        if(!existingUser.getBirthDate().equals(newUser.getBirthDate())){
            log.info("Birth date updated for user {}", email);
            existingUser.setBirthDate(newUser.getBirthDate());
            updated = true;
        }
        if(!existingUser.getFirstName().equals(newUser.getFirstName())){
            log.info("First Name updated for user {}", email);
            existingUser.setFirstName(newUser.getFirstName());
            updated = true;
        }
        if(!existingUser.getLastName().equals(newUser.getLastName())){
            log.info("Last name updated for user {}", email);
            existingUser.setLastName(newUser.getLastName());
            updated = true;
        }
        if(!existingUser.getPhoneNumber().equals(newUser.getPhoneNumber())){
            log.info("Phone number updated for user {}", email);
            existingUser.setPhoneNumber(newUser.getPhoneNumber());
            updated = true;
        }

        if(updated){
            existingUser.setLastModifDate(new Date());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.getOneByEmail(s);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority());
    }

    private List<GrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
