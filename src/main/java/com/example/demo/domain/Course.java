package com.example.demo.domain;

//课程表
public class Course {
    private String kh;      //课号
    private String km;      //课名
    private Integer xf;         //学分
    private Integer xs;         //学时
    private double cjRatio; //平时成绩占比
    private String yxh;     //院系号


    public Course(){

    }

    public Course(String kh, String km, Integer xf, Integer xs, double cjRatio,String yxh) {
        this.kh = kh;
        this.km = km;
        this.xf = xf;
        this.xs = xs;
        this.cjRatio=cjRatio;
        this.yxh = yxh;
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    public double getCjRatio() {
        return cjRatio;
    }

    public void setCjRatio(double cjRatio) {
        this.cjRatio = cjRatio;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public Integer getXf() {
        return xf;
    }

    public void setXf(int xf) {
        this.xf = xf;
    }

    public Integer getXs() {
        return xs;
    }

    public void setXs(int xs) {
        this.xs = xs;
    }

    public String getYxh() {
        return yxh;
    }

    public void setYxh(String yxh) {
        this.yxh = yxh;
    }
}
