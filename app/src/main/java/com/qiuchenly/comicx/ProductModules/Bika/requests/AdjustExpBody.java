package com.qiuchenly.comicx.ProductModules.Bika.requests;

public class AdjustExpBody {
    int exp;
    String userId;

    public AdjustExpBody(int exp, String userId) {
        this.exp = exp;
        this.userId = userId;
    }

    public int getExp() {
        return this.exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString() {
        return "AdjustExpBody{exp=" + this.exp + ", userId='" + this.userId + '\'' + '}';
    }
}
