package com.qiuchenly.comicx.ProductModules.Bika;

public class PicaAppObject {
    String description;
    String icon;
    String title;
    String url;

    public PicaAppObject(String title, String url, String icon, String description) {
        this.title = title;
        this.url = url;
        this.icon = icon;
        this.description = description;
    }

    public String toString() {
        return "PicaAppObject{title='" + this.title + '\'' + ", url='" + this.url + '\'' + ", icon='" + this.icon + '\'' + ", description='" + this.description + '\'' + '}';
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
