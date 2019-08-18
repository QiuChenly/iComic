package com.qiuchenly.comicx.ProductModules.Bika;

public class SingleImageTextViewObject {
    ThumbnailObject image;
    String title;

    public SingleImageTextViewObject(ThumbnailObject image, String title) {
        this.image = image;
        this.title = title;
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

    public String toString() {
        return "ProfileComicImageWithTextViewObject{image=" + this.image + ", title='" + this.title + '\'' + '}';
    }
}
