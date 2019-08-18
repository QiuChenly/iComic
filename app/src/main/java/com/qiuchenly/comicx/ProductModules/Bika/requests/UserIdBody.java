package com.qiuchenly.comicx.ProductModules.Bika.requests;

public class UserIdBody {
    String userId;

    public UserIdBody(String str) {
        this.userId = str;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }
}