package com.example.demo.domain;

//开课表
public class Open {
    private String xq;      //学期
    private String kh;      //课号
    private String gh;      //教师工号
    private String sksj;    //上课时间

    public Open(String xq, String kh, String gh, String sksj) {
        this.xq = xq;
        this.kh = kh;
        this.gh = gh;
        this.sksj = sksj;
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq;
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    public String getGh() {
        return gh;
    }

    public void setGh(String gh) {
        this.gh = gh;
    }

    public String getSksj() {
        return sksj;
    }

    public void setSksj(String sksj) {
        this.sksj = sksj;
    }
}
