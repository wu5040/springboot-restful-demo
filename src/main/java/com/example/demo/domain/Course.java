package com.example.demo.domain;

public class Course {
    private String kh;
    private String km;
    private int xf;
    private int xs;
    private double cjRatio;
    private String yxh;


    public Course(String kh, String km, int xf, int xs, double cjRatio,String yxh) {
        this.kh = kh;
        this.km = km;
        this.xf = xf;
        this.xs = xs;
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

    public int getXf() {
        return xf;
    }

    public void setXf(int xf) {
        this.xf = xf;
    }

    public int getXs() {
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
