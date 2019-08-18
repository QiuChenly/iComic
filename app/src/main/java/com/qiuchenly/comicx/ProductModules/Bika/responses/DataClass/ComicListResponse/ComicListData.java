package com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse;

import com.qiuchenly.comicx.ProductModules.Bika.ComicListObject;

import java.util.ArrayList;

public class ComicListData {
    ArrayList<ComicListObject> docs;
    int limit;
    int page;
    int pages;
    int total;

    public ComicListData(int total, int limit, int page, int pages, ArrayList<ComicListObject> docs) {
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

    public ArrayList<ComicListObject> getDocs() {
        return this.docs;
    }

    public void setDocs(ArrayList<ComicListObject> docs) {
        this.docs = docs;
    }

    public String toString() {
        return "ComicListData{total=" + this.total + ", limit=" + this.limit + ", page=" + this.page + ", pages=" + this.pages + ", docs=" + this.docs + '}';
    }
}
