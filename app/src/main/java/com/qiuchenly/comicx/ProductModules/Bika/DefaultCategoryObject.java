package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class DefaultCategoryObject {
    @SerializedName("_id")
    String categoryId;
    String description;
    int thumbId;
    String title;

    public DefaultCategoryObject(String categoryId, String title, String description, int thumbId) {
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.thumbId = thumbId;
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

    public int getThumbId() {
        return this.thumbId;
    }

    public void setThumbId(int thumbId) {
        this.thumbId = thumbId;
    }

    public String toString() {
        return "CategoryObject{categoryId='" + this.categoryId + '\'' + ", title='" + this.title + '\'' + ", description='" + this.description + '\'' + ", thumbId=" + this.thumbId + '}';
    }
}
