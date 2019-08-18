package com.qiuchenly.comicx.ProductModules.Bika.requests;

import com.google.gson.annotations.SerializedName;

public class SignInBody {
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;

    public SignInBody(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
