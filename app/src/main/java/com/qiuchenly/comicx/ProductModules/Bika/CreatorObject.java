package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class CreatorObject {
    ThumbnailObject avatar;
    @SerializedName("_id")
    String creatorId;
    String gender;
    String name;

    public CreatorObject(String creatorId, String name, String gender, ThumbnailObject avatar) {
        this.creatorId = creatorId;
        this.name = name;
        this.gender = gender;
        this.avatar = avatar;
    }

    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ThumbnailObject getAvatar() {
        return this.avatar;
    }

    public void setAvatar(ThumbnailObject avatar) {
        this.avatar = avatar;
    }
}
