package com.example.demo.domain;


//用户信息类
public class User {
    private String sno;
    private String password;
    private String name;

    public User() {

    }

    public User(String sno, String password) {
        this.sno = sno;
        this.password = password;
    }

    public User(String sno, String password, String name) {
        this.sno = sno;
        this.password = password;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public String getSno() {
        return sno;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public void setPassword(String password) { this.password = password; }
}