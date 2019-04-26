package com.example.demo.domain;

import java.sql.Date;

public class Teacher{
    private String gh;
    private String xl;      //学历
    private String jbgz;    //基本工资
    private String yxh;     //院系号——>Department


    public Teacher(String gh,String xl,String jbgz,String yxh){
        this.gh=gh;
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
