package com.application.base.converter;

import com.application.base.bean.UserRequest;
import com.application.base.entity.User;
import com.application.base.entity.UserInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private BCryptPasswordEncoder encoder;

    public UserConverter(BCryptPasswordEncoder encoder){
        this.encoder = encoder;
    }

    public User convertToUser(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(encoder.encode(userRequest.getPassword()));
        return user;
    }

    public UserInfo convertToUserInfo(UserRequest userRequest){
        UserInfo userInfo = new UserInfo();
        userInfo.setBirthDate(userRequest.getBirthDate());
        userInfo.setFirstName(userRequest.getFirstName());
        userInfo.setLastName(userRequest.getLastName());
        userInfo.setPhoneNumber(userRequest.getPhoneNumber());
        return userInfo;
    }

    public User convert(UserRequest userRequest){
        UserInfo userInfo = convertToUserInfo(userRequest);
        User user = convertToUser(userRequest);
        user.setUserInfo(userInfo);
        return user;
    }
}
