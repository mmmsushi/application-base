package com.application.base.controller;

import com.application.base.bean.UserRequest;
import com.application.base.converter.UserConverter;
import com.application.base.entity.User;
import com.application.base.exception.business.BusinessException;
import com.application.base.exception.technical.TechnicalException;
import com.application.base.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login", produces = { MediaType.APPLICATION_JSON_VALUE })

public class LoginController {

    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    private LoginController(UserService userService, UserConverter userConverter){
        this.userConverter = userConverter;
        this.userService = userService;
    }

    @PostMapping(value = "/new")
    public void addUser(@RequestBody UserRequest userRequest) throws TechnicalException, BusinessException {
        User user = userConverter.convert(userRequest);
        userService.saveUser(user, true);
    }
}
