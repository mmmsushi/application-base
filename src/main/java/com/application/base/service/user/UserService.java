package com.application.base.service.user;

import com.application.base.entity.User;
import com.application.base.exception.business.BusinessException;
import com.application.base.exception.technical.AuthenticationException;
import com.application.base.exception.technical.DaoException;

import java.util.List;

public interface UserService {

    void saveUser(User user, boolean isCreation) throws DaoException, BusinessException;

    List<User> getAll() throws DaoException;

    User getByEmail(String email) throws DaoException;

    void updatePassword(String email, String lastPassword, String newPassword) throws AuthenticationException;
}
