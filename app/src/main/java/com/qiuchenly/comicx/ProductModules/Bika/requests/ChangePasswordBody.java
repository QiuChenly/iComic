package com.qiuchenly.comicx.ProductModules.Bika.requests;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordBody {
    @SerializedName("new_password")
    String newPassword;
    @SerializedName("old_password")
    String oldPassword;

    public ChangePasswordBody(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String toString() {
        return "ChangePasswordBody{oldPassword='" + this.oldPassword + '\'' + ", newPassword='" + this.newPassword + '\'' + '}';
    }
}
