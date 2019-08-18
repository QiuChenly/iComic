package com.qiuchenly.comicx.ProductModules.Bika.responses;

public class PunchInResponse {
    PunchInObject res;

    public PunchInResponse(PunchInObject res) {
        this.res = res;
    }

    public PunchInObject getRes() {
        return this.res;
    }

    public void setRes(PunchInObject res) {
        this.res = res;
    }

    public String toString() {
        return "PunchInResponse{res=" + this.res + '}';
    }
}
