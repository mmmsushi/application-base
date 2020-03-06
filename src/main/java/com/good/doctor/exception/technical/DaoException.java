package com.good.doctor.exception.technical;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoException extends TechnicalException {
    public DaoException(String message) {
        super(message);
    }
}
