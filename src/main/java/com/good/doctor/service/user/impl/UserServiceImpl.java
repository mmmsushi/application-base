package com.good.doctor.service.user.impl;

import com.good.doctor.entity.User;
import com.good.doctor.exception.business.BusinessException;
import com.good.doctor.exception.technical.DaoException;
import com.good.doctor.repository.UserRepository;
import com.good.doctor.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
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
                    updateUserField(user, existedUser);
                    userRepository.saveAndFlush(existedUser);
                } else if(existedUser != null){
                    throw new BusinessException("User " + user.getEmail() + " already exists.");
                } else if(existedUser == null){
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

    private void updateUserField(User newUser, User existingUser){
        boolean updated = false;

        if(!existingUser.getBirthDate().equals(newUser.getBirthDate())){
            log.info("Birth date updated for user {}", existingUser.getEmail());
            existingUser.setBirthDate(newUser.getBirthDate());
            updated = true;
        }
        if(!existingUser.getFirstName().equals(newUser.getFirstName())){
            log.info("First Name updated for user {}", existingUser.getEmail());
            existingUser.setFirstName(newUser.getFirstName());
            updated = true;
        }
        if(!existingUser.getLastName().equals(newUser.getLastName())){
            log.info("Last name updated for user {}", existingUser.getEmail());
            existingUser.setLastName(newUser.getLastName());
            updated = true;
        }
        if(!existingUser.getPhoneNumber().equals(newUser.getPhoneNumber())){
            log.info("Phone number updated for user {}", existingUser.getEmail());
            existingUser.setPhoneNumber(newUser.getPhoneNumber());
            updated = true;
        }

        if(updated){
            existingUser.setLastModifDate(new Date());
        }
    }
}
