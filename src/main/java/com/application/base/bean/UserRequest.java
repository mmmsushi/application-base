package com.application.base.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserRequest {

    private String email;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Europe/Paris")
    private Date birthDate;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
