package com.example.demo.domain;


public class Student extends User {

    private String sno;
    private String jg;      //籍贯
    private String sjhm;    //手机号码
    private String yxh;     //院系号


    public Student(String sno,String jg,String sjhm,String yxh){

        this.sno=sno;
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
