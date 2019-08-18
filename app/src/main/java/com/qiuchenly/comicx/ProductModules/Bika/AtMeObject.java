package com.qiuchenly.comicx.ProductModules.Bika;

public class AtMeObject {
    int index;
    String name;

    public AtMeObject(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void addIndex() {
        this.index++;
    }
}
