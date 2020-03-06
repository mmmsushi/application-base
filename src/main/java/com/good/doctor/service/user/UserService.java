package com.good.doctor.service.user;

import com.good.doctor.entity.User;
import com.good.doctor.exception.DaoException;

import java.util.List;

public interface UserService {

    void addUser(User user) throws DaoException;

    List<User> getAll() throws DaoException;

    User getByEmail(String email) throws DaoException;
}
