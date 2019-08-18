package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameDetailObject {
    boolean adult;
    boolean android;
    ArrayList<String> androidLinks;
    float androidSize;
    int commentsCount;
    String description;
    int downloadsCount;
    @SerializedName("_id")
    String gameId;
    ThumbnailObject icon;
    boolean ios;
    ArrayList<String> iosLinks;
    float iosSize;
    boolean isLiked;
    int likesCount;
    String publisher;
    ArrayList<ThumbnailObject> screenshots;
    boolean suggest;
    String title;
    String updateContent;
    String version;
    String videoLink;

    public GameDetailObject(String gameId, String title, String version, String publisher, ThumbnailObject icon, String updateContent, String description, String videoLink, int downloadsCount, int commentsCount, int likesCount, boolean isLiked, boolean suggest, boolean adult, boolean ios, boolean android, float iosSize, float androidSize, ArrayList<String> iosLinks, ArrayList<String> androidLinks, ArrayList<ThumbnailObject> screenshots) {
        this.gameId = gameId;
        this.title = title;
        this.version = version;
        this.publisher = publisher;
        this.icon = icon;
        this.updateContent = updateContent;
        this.description = description;
        this.videoLink = videoLink;
        this.downloadsCount = downloadsCount;
        this.commentsCount = commentsCount;
        this.likesCount = likesCount;
        this.isLiked = isLiked;
        this.suggest = suggest;
        this.adult = adult;
        this.ios = ios;
        this.android = android;
        this.iosSize = iosSize;
        this.androidSize = androidSize;
        this.iosLinks = iosLinks;
        this.androidLinks = androidLinks;
        this.screenshots = screenshots;
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

    public ThumbnailObject getIcon() {
        return this.icon;
    }

    public void setIcon(ThumbnailObject icon) {
        this.icon = icon;
    }

    public String getUpdateContent() {
        return this.updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoLink() {
        return this.videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public int getDownloadsCount() {
        return this.downloadsCount;
    }

    public void setDownloadsCount(int downloadsCount) {
        this.downloadsCount = downloadsCount;
    }

    public int getCommentsCount() {
        return this.commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikesCount() {
        return this.likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isLiked() {
        return this.isLiked;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
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

    public float getIosSize() {
        return this.iosSize;
    }

    public void setIosSize(float iosSize) {
        this.iosSize = iosSize;
    }

    public float getAndroidSize() {
        return this.androidSize;
    }

    public void setAndroidSize(float androidSize) {
        this.androidSize = androidSize;
    }

    public ArrayList<String> getIosLinks() {
        return this.iosLinks;
    }

    public void setIosLinks(ArrayList<String> iosLinks) {
        this.iosLinks = iosLinks;
    }

    public ArrayList<String> getAndroidLinks() {
        return this.androidLinks;
    }

    public void setAndroidLinks(ArrayList<String> androidLinks) {
        this.androidLinks = androidLinks;
    }

    public ArrayList<ThumbnailObject> getScreenshots() {
        return this.screenshots;
    }

    public void setScreenshots(ArrayList<ThumbnailObject> screenshots) {
        this.screenshots = screenshots;
    }

    public String toString() {
        return "GameDetailObject{gameId='" + this.gameId + '\'' + ", title='" + this.title + '\'' + ", version='" + this.version + '\'' + ", publisher='" + this.publisher + '\'' + ", icon=" + this.icon + ", updateContent='" + this.updateContent + '\'' + ", description='" + this.description + '\'' + ", videoLink='" + this.videoLink + '\'' + ", downloadsCount=" + this.downloadsCount + ", commentsCount=" + this.commentsCount + ", likesCount=" + this.likesCount + ", isLiked=" + this.isLiked + ", suggest=" + this.suggest + ", adult=" + this.adult + ", ios=" + this.ios + ", android=" + this.android + ", iosSize=" + this.iosSize + ", androidSize=" + this.androidSize + ", iosLinks=" + this.iosLinks + ", androidLinks=" + this.androidLinks + ", screenshots=" + this.screenshots + '}';
    }
}
