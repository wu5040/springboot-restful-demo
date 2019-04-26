package com.example.demo.domain;


import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

//用户信息类
public class User {
    private String userId;
    private String password;
    private String name;
    private String gender;
    private String role;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    public User() {

    }

    public User(String userId, String password,String role) {
        this.userId = userId;
        this.password = password;
        this.role=role;
    }

    public User(String userId, String password, String name,String gender,Date birthday,String role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender=gender;
        this.birthday=birthday;
        this.role=role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}