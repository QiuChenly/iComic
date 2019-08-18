package com.qiuchenly.comicx.ProductModules.Bika.responses;

import com.qiuchenly.comicx.ProductModules.Bika.ComicListObject;

import java.util.ArrayList;

public class ComicRandomListResponse {
    ArrayList<ComicListObject> comics;

    public ComicRandomListResponse(ArrayList<ComicListObject> comics) {
        this.comics = comics;
    }

    public ArrayList<ComicListObject> getComics() {
        return this.comics;
    }

    public void setComics(ArrayList<ComicListObject> comics) {
        this.comics = comics;
    }
}
