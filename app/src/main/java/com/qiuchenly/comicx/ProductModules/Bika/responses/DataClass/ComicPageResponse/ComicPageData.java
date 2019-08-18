package com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicPageResponse;


import com.qiuchenly.comicx.ProductModules.Bika.ComicPageObject;

import java.util.ArrayList;

public class ComicPageData {
    ArrayList<ComicPageObject> docs;
    int limit;
    int page;
    int pages;
    int total;

    public ComicPageData(int total, int limit, int page, int pages, ArrayList<ComicPageObject> docs) {
        this.total = total;
        this.limit = limit;
        this.page = page;
        this.pages = pages;
        this.docs = docs;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public ArrayList<ComicPageObject> getDocs() {
        return this.docs;
    }

    public void setDocs(ArrayList<ComicPageObject> docs) {
        this.docs = docs;
    }

    public String toString() {
        return "ComicPageData{total=" + this.total + ", limit=" + this.limit + ", page=" + this.page + ", pages=" + this.pages + ", docs=" + this.docs + '}';
    }
}
