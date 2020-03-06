package com.good.doctor.service.user.impl;

import com.good.doctor.entity.User;
import com.good.doctor.exception.DaoException;
import com.good.doctor.repository.UserRepository;
import com.good.doctor.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void addUser(User user) throws DaoException {
        try {
            if (user != null) {
                User existedUser = userRepository.getOneByEmail(user.getEmail());
                if (existedUser == null) {
                    log.info("New user created in database : {}", user.getEmail());
                    userRepository.saveAndFlush(user);
                } else {
                    log.info("Updating existing user");
                    updateUserField(user, existedUser);
                    userRepository.saveAndFlush(existedUser);
                }
            }
        } catch (Exception e){
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

        if(existingUser.getBirthDate().equals(newUser.getBirthDate())){
            log.info("Birth date updated for user {}", existingUser.getEmail());
            existingUser.setBirthDate(newUser.getBirthDate());
            updated = true;
        }
        if(existingUser.getFirstName().equals(newUser.getFirstName())){
            log.info("First Name updated for user {}", existingUser.getEmail());
            existingUser.setFirstName(newUser.getFirstName());
            updated = true;
        }
        if(existingUser.getLastName().equals(newUser.getLastName())){
            log.info("Last name updated for user {}", existingUser.getEmail());
            existingUser.setLastName(newUser.getLastName());
            updated = true;
        }
        if(existingUser.getPhoneNumber().equals(newUser.getPhoneNumber())){
            log.info("Phone number updated for user {}", existingUser.getEmail());
            existingUser.setPhoneNumber(newUser.getPhoneNumber());
            updated = true;
        }

        if(updated){
            existingUser.setLastModifDate(new Date());
        }
    }
}
