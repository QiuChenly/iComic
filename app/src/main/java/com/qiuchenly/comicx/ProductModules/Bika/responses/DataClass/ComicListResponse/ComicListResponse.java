package com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse;

public class ComicListResponse {
    ComicListData comics;

    public ComicListResponse(ComicListData comics) {
        this.comics = comics;
    }

    public ComicListData getComics() {
        return this.comics;
    }

    public void setComics(ComicListData comics) {
        this.comics = comics;
    }

    public String toString() {
        return "ComicListResponse{, comics=" + this.comics + '}';
    }
}
