package com.qiuchenly.comicx.ProductModules.Bika;

public class SupportUsPayPalObject {
    public String description;
    public int imageId;
    public String price;
    public String priceUnit;
    public String title;

    public SupportUsPayPalObject(int imageId, String title, String priceUnit, String price, String description) {
        this.imageId = imageId;
        this.title = title;
        this.priceUnit = priceUnit;
        this.price = price;
        this.description = description;
    }

    public int getImageId() {
        return this.imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriceUnit() {
        return this.priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "SupportUsPayPalObject{imageId=" + this.imageId + ", title='" + this.title + '\'' + ", priceUnit='" + this.priceUnit + '\'' + ", price='" + this.price + '\'' + ", description='" + this.description + '\'' + '}';
    }
}
