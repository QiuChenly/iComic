package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class LatestApplicationObject {
    ApkObject apk;
    @SerializedName("created_at")
    String createdAt;
    String downloadUrl;
    @SerializedName("_id")
    String latestApplicationId;
    String updateContent;
    @SerializedName("updated_at")
    String updatedAt;
    String version;

    public LatestApplicationObject(String latestApplicationId, String version, String updateContent, String downloadUrl, ApkObject apk, String createdAt, String updatedAt) {
        this.latestApplicationId = latestApplicationId;
        this.version = version;
        this.updateContent = updateContent;
        this.downloadUrl = downloadUrl;
        this.apk = apk;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getLatestApplicationId() {
        return this.latestApplicationId;
    }

    public void setLatestApplicationId(String latestApplicationId) {
        this.latestApplicationId = latestApplicationId;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateContent() {
        return this.updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public ApkObject getApk() {
        return this.apk;
    }

    public void setApk(ApkObject apk) {
        this.apk = apk;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toString() {
        return "LatestApplicationData{latestApplicationId='" + this.latestApplicationId + '\'' + ", version='" + this.version + '\'' + ", updateContent='" + this.updateContent + '\'' + ", downloadUrl='" + this.downloadUrl + '\'' + ", apk=" + this.apk + ", createdAt='" + this.createdAt + '\'' + ", updatedAt='" + this.updatedAt + '\'' + '}';
    }
}
