package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class CategoryObject {
    @SerializedName("_id")
    String categoryId;
    String description;
    ThumbnailObject thumb;
    String title;
    Boolean isWeb;
    Boolean active;
    String link;

    public CategoryObject(String categoryId, String title, String description, ThumbnailObject thumb) {
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.thumb = thumb;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getWeb() {
        return isWeb;
    }

    public void setWeb(Boolean web) {
        isWeb = web;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ThumbnailObject getThumb() {
        return this.thumb;
    }

    public void setThumb(ThumbnailObject thumb) {
        this.thumb = thumb;
    }

    public String toString() {
        return "CategoryObject{categoryId='" + this.categoryId + '\'' + ", title='" + this.title + '\'' + ", description='" + this.description + '\'' + ", thumb=" + this.thumb + '}';
    }
}
