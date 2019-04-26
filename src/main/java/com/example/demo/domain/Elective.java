package com.example.demo.domain;

public class Elective {
    private String xh;
    private String xq;
    private String kh;
    private String gh;
    private double pscj;
    private double kscj;
    private double zpcj;

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
