package com.good.doctor.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoException extends Exception {
    public DaoException(String message) {
        super(message);
    }
}
