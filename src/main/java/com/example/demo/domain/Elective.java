package com.example.demo.domain;

//选课表-成绩表
public class Elective {
    private String xh;      //学号
    private String xq;      //学期
    private String kh;      //课程号
    private String gh;      //教师工号
    private double pscj;    //平时成绩
    private double kscj;    //考试成绩
    private double zpcj;    //总评成绩

    public Elective(){

    }

    public Elective(String xh, String xq, String kh, String gh){
        this.xh = xh;
        this.xq = xq;
        this.kh = kh;
        this.gh = gh;
    }

    public Elective(String xh, String xq, String kh, String gh, double pscj, double kscj, double zpcj) {
        this.xh = xh;
        this.xq = xq;
        this.kh = kh;
        this.gh = gh;
        this.pscj = pscj;
        this.kscj = kscj;
        this.zpcj = zpcj;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
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

    public double getPscj() {
        return pscj;
    }

    public void setPscj(double pscj) {
        this.pscj = pscj;
    }

    public double getKscj() {
        return kscj;
    }

    public void setKscj(double kscj) {
        this.kscj = kscj;
    }

    public double getZpcj() {
        return zpcj;
    }

    public void setZpcj(double zpcj) {
        this.zpcj = zpcj;
    }
}
