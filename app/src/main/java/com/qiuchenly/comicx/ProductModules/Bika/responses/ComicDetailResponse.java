package com.qiuchenly.comicx.ProductModules.Bika.responses;


import com.qiuchenly.comicx.ProductModules.Bika.ComicDetailObject;

public class ComicDetailResponse {
    ComicDetailObject comic;

    public ComicDetailResponse(ComicDetailObject comic) {
        this.comic = comic;
    }

    public ComicDetailObject getComic() {
        return this.comic;
    }

    public void setComic(ComicDetailObject comic) {
        this.comic = comic;
    }

    public String toString() {
        return "ComicDetailResponse{comic=" + this.comic + '}';
    }
}
