package com.example.demo.domain;

import java.sql.Date;

public class Teacher extends User {
    private String xl;
    private String jbgz;
    private String yxh;

    public Teacher(String gh,String password){
        super(gh,password,"teacher");
    }

    public Teacher(String gh, String password, String xm,String xb, Date csrq,String xl,String jbgz,String yxh){
        super(gh,password,xm,xb,csrq,"teacher");
        this.xl=xl;
        this.jbgz=jbgz;
        this.yxh=yxh;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getJbgz() {
        return jbgz;
    }

    public void setJbgz(String jbgz) {
        this.jbgz = jbgz;
    }

    public String getYxh() {
        return yxh;
    }

    public void setYxh(String yxh) {
        this.yxh = yxh;
    }
}
