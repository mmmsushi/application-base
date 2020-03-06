package com.good.doctor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
