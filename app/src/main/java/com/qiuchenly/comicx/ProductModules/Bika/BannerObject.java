package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class BannerObject {
    String bannerId;
    @SerializedName("_comic")
    String comicId;
    @SerializedName("_game")
    String gameId;
    String link;
    String shortDescription;
    ThumbnailObject thumb;
    String title;
    String type;

    public BannerObject(String bannerId, String title, String type, String shortDescription, String gameId, String comicId, String link, ThumbnailObject thumb) {
        this.bannerId = bannerId;
        this.title = title;
        this.type = type;
        this.shortDescription = shortDescription;
        this.gameId = gameId;
        this.comicId = comicId;
        this.link = link;
        this.thumb = thumb;
    }

    public String getBannerId() {
        return this.bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getGameId() {
        return this.gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getComicId() {
        return this.comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ThumbnailObject getThumb() {
        return this.thumb;
    }

    public void setThumb(ThumbnailObject thumb) {
        this.thumb = thumb;
    }
}
