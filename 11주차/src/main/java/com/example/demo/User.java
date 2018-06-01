package com.example.demo;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name= "userinfo")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String password;
}
