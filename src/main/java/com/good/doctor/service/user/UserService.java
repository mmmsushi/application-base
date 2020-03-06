package com.good.doctor.service.user;

import com.good.doctor.entity.User;
import com.good.doctor.exception.business.BusinessException;
import com.good.doctor.exception.technical.DaoException;

import java.util.List;

public interface UserService {

    void saveUser(User user, boolean isCreation) throws DaoException, BusinessException;

    List<User> getAll() throws DaoException;

    User getByEmail(String email) throws DaoException;
}
