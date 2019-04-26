package com.example.demo.domain;

public class Department {
    private String yxh;
    private String mc;
    private String dz;
    private String lxdh;

    public Department(){

    }

    public Department(String yxh, String mc, String dz, String lxdh) {
        this.yxh = yxh;
        this.mc = mc;
        this.dz = dz;
        this.lxdh = lxdh;
    }

    public String getYxh() {
        return yxh;
    }

    public void setYxh(String yxh) {
        this.yxh = yxh;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }
}
