package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class ComicPageObject {
    @SerializedName("_id")
    String comicPageId;
    ThumbnailObject media;

    public ComicPageObject(String comicPageId, ThumbnailObject media) {
        this.comicPageId = comicPageId;
        this.media = media;
    }

    public String getComicPageId() {
        return this.comicPageId;
    }

    public void setComicPageId(String comicPageId) {
        this.comicPageId = comicPageId;
    }

    public ThumbnailObject getMedia() {
        return this.media;
    }

    public void setMedia(ThumbnailObject media) {
        this.media = media;
    }

    public String toString() {
        return "ComicPageObject{comicPageId='" + this.comicPageId + '\'' + ", media=" + this.media + '}';
    }
}
