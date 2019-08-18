package com.qiuchenly.comicx.ProductModules.Bika.requests;

public class UpdateProfileBody {
    String slogan;

    public UpdateProfileBody(String slogan) {
        this.slogan = slogan;
    }

    public String getSlogan() {
        return this.slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String toString() {
        return "UpdateProfileBody{slogan='" + this.slogan + '\'' + '}';
    }
}
