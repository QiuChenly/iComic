package com.qiuchenly.comicx.ProductModules.Bika;

public class NewsObject {
    String description;
    ThumbnailObject image;
    String title;

    public NewsObject(ThumbnailObject image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public ThumbnailObject getImage() {
        return this.image;
    }

    public void setImage(ThumbnailObject image) {
        this.image = image;
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

    public String toString() {
        return "NewsObject{image=" + this.image + ", title='" + this.title + '\'' + ", description='" + this.description + '\'' + '}';
    }
}
