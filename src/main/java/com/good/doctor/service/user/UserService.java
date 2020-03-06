package com.good.doctor.service.user;

import com.good.doctor.entity.User;

import java.util.List;

public interface UserService {

    void addUser(User user);
    List<User> getAll();
}
