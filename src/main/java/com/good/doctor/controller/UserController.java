package com.good.doctor.controller;

import com.good.doctor.entity.User;
import com.good.doctor.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE })

public class UserController {

    private final UserService userService;

    @Autowired
    private UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/getAll")
    public List<User> getAll(){
        return userService.getAll();
    }

}
