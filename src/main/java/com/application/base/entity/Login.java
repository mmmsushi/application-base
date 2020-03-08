package com.application.base.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="login")
public class Login implements Serializable {

    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name="password")
    private String password;
}
