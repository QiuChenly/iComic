package com.qiuchenly.comicx.ProductModules.Bika.responses;

import com.qiuchenly.comicx.ProductModules.Bika.BannerObject;

import java.util.ArrayList;

public class BannersResponse {
    ArrayList<BannerObject> banners;

    public BannersResponse(ArrayList<BannerObject> banners) {
        this.banners = banners;
    }

    public ArrayList<BannerObject> getBanners() {
        return this.banners;
    }

    public void setBanners(ArrayList<BannerObject> banners) {
        this.banners = banners;
    }
}
