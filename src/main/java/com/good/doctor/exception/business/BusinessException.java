package com.good.doctor.exception.business;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }
}
