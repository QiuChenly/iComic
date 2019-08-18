package com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.AnnouncementsResponse;

import com.qiuchenly.comicx.ProductModules.Bika.AnnouncementObject;

import java.util.ArrayList;

public class AnnouncementsData {
    ArrayList<AnnouncementObject> docs;
    int limit;
    int page;
    int pages;
    int total;

    public AnnouncementsData(int total, int limit, int page, int pages, ArrayList<AnnouncementObject> docs) {
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

    public ArrayList<AnnouncementObject> getDocs() {
        return this.docs;
    }

    public void setDocs(ArrayList<AnnouncementObject> docs) {
        this.docs = docs;
    }
}
