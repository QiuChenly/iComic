package com.qiuchenly.comicx.ProductModules.Bika.requests;

public class ForgotPasswordBody {
    String email;

    public ForgotPasswordBody(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return "ForgotPasswordBody{email='" + this.email + '\'' + '}';
    }
}
