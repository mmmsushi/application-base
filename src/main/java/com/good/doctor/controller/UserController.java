package com.good.doctor.controller;

import com.good.doctor.entity.User;
import com.good.doctor.exception.DaoException;
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
    public List<User> getAll() throws DaoException {
        return userService.getAll();
    }

    @PostMapping(value = "/save")
    public void save(@RequestBody User user) throws DaoException {
        userService.addUser(user);
    }

    @GetMapping(value = "/getByEmail")
    public User getByEmail(@RequestParam("email") String email) throws DaoException { return userService.getByEmail(email);}

}
