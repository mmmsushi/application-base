package com.good.doctor.controller;

import com.good.doctor.entity.User;
import com.good.doctor.exception.business.BusinessException;
import com.good.doctor.exception.technical.TechnicalException;
import com.good.doctor.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public List<User> getAll() throws TechnicalException {
        return userService.getAll();
    }

    @PostMapping(value = "/new")
    public void addUser(@RequestBody User user) throws TechnicalException, BusinessException {
        userService.saveUser(user, true);
    }

    @PostMapping(value = "/update")
    public void updateUser(@RequestBody User user) throws TechnicalException, BusinessException {
        userService.saveUser(user, false);
    }

    @GetMapping(value = "/getByEmail")
    public User getByEmail(@RequestParam("email") String email) throws TechnicalException { return userService.getByEmail(email);}

}
