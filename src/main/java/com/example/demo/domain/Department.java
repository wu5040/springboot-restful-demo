package com.example.demo.domain;

//院系表
public class Department {
    private String yxh;     //院系号
    private String mc;      //名称
    private String dz;      //地址
    private String lxdh;    //联系电话

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
