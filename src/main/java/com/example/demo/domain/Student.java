package com.example.demo.domain;


import java.sql.Date;

public class Student extends User {

    private String jg;
    private String sjhm;
    private String yxh;

    public Student(String sno,String password){
        super(sno,password,"student");
    }


    public Student(String sno,String password,String xm,String xb,Date csrq,String jg,String sjhm,String yxh){
        super(sno,password,xm,xb,csrq,"student");

        this.jg=jg;
        this.sjhm=sjhm;
        this.yxh=yxh;
    }


    public String getJg(){
        return jg;
    }
    public String getSjhm(){
        return sjhm;
    }
    public String getYxh(){
        return yxh;
    }



    public void setJg(String jg){
        this.jg=jg;
    }
    public void setSjhm(String sjhm){
        this.sjhm=sjhm;
    }
    public void setYxh(String yxh){
        this.yxh=yxh;
    }

}
