package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class GameListObject {
    boolean adult;
    boolean android;
    @SerializedName("_id")
    String gameId;
    ThumbnailObject icon;
    boolean ios;
    int likesCount;
    String publisher;
    boolean suggest;
    String title;
    String version;

    public GameListObject(String gameId, String title, String version, String publisher, int likesCount, boolean suggest, boolean adult, boolean ios, boolean android, ThumbnailObject icon) {
        this.gameId = gameId;
        this.title = title;
        this.version = version;
        this.publisher = publisher;
        this.likesCount = likesCount;
        this.suggest = suggest;
        this.adult = adult;
        this.ios = ios;
        this.android = android;
        this.icon = icon;
    }

    public String getGameId() {
        return this.gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getLikesCount() {
        return this.likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isSuggest() {
        return this.suggest;
    }

    public void setSuggest(boolean suggest) {
        this.suggest = suggest;
    }

    public boolean isAdult() {
        return this.adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isIos() {
        return this.ios;
    }

    public void setIos(boolean ios) {
        this.ios = ios;
    }

    public boolean isAndroid() {
        return this.android;
    }

    public void setAndroid(boolean android) {
        this.android = android;
    }

    public ThumbnailObject getIcon() {
        return this.icon;
    }

    public void setIcon(ThumbnailObject icon) {
        this.icon = icon;
    }

    public String toString() {
        return "GameListObject{gameId='" + this.gameId + '\'' + ", title='" + this.title + '\'' + ", version='" + this.version + '\'' + ", publisher='" + this.publisher + '\'' + ", likesCount=" + this.likesCount + ", suggest=" + this.suggest + ", adult=" + this.adult + ", ios=" + this.ios + ", android=" + this.android + ", icon=" + this.icon + '}';
    }
}
