package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ComicDetailObject {
    String author;
    ArrayList<String> categories;
    String chineseTeam;
    @SerializedName("_id")
    String comicId;
    int commentsCount;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("_creator")
    CreatorObject creator;
    String description;
    @SerializedName("epsCount")
    int episodeCount;
    boolean finished;
    boolean isFavourite;
    boolean isLiked;
    int likesCount;
    int pagesCount;
    ArrayList<String> tags;
    ThumbnailObject thumb;
    String title;
    @SerializedName("updated_at")
    String updatedAt;
    int viewsCount;

    public ComicDetailObject(String comicId, String title, String author, String description, String chineseTeam, CreatorObject creator, ThumbnailObject thumb, ArrayList<String> categories, ArrayList<String> tags, int commentsCount, int pagesCount, int episodeCount, int likesCount, int viewsCount, boolean finished, boolean isFavourite, boolean isLiked, String updatedAt, String createdAt) {
        this.comicId = comicId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.chineseTeam = chineseTeam;
        this.creator = creator;
        this.thumb = thumb;
        this.categories = categories;
        this.tags = tags;
        this.commentsCount = commentsCount;
        this.pagesCount = pagesCount;
        this.episodeCount = episodeCount;
        this.likesCount = likesCount;
        this.viewsCount = viewsCount;
        this.finished = finished;
        this.isFavourite = isFavourite;
        this.isLiked = isLiked;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public void updateWithComicDetailObject(ComicDetailObject comicDetailObject) {
        this.comicId = comicDetailObject.comicId;
        this.title = comicDetailObject.title;
        this.author = comicDetailObject.author;
        this.description = comicDetailObject.description;
        this.chineseTeam = comicDetailObject.chineseTeam;
        this.creator = comicDetailObject.creator;
        this.thumb = comicDetailObject.thumb;
        this.categories = comicDetailObject.categories;
        this.tags = comicDetailObject.tags;
        this.commentsCount = comicDetailObject.commentsCount;
        this.pagesCount = comicDetailObject.pagesCount;
        this.episodeCount = comicDetailObject.episodeCount;
        this.likesCount = comicDetailObject.likesCount;
        this.viewsCount = comicDetailObject.viewsCount;
        this.finished = comicDetailObject.finished;
        this.isFavourite = comicDetailObject.isFavourite;
        this.isLiked = comicDetailObject.isLiked;
        this.updatedAt = comicDetailObject.updatedAt;
        this.createdAt = comicDetailObject.createdAt;
    }

    public String getComicId() {
        return this.comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChineseTeam() {
        return this.chineseTeam;
    }

    public void setChineseTeam(String chineseTeam) {
        this.chineseTeam = chineseTeam;
    }

    public CreatorObject getCreator() {
        return this.creator;
    }

    public void setCreator(CreatorObject creator) {
        this.creator = creator;
    }

    public ThumbnailObject getThumb() {
        return this.thumb;
    }

    public void setThumb(ThumbnailObject thumb) {
        this.thumb = thumb;
    }

    public ArrayList<String> getCategories() {
        return this.categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getCommentsCount() {
        return this.commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getPagesCount() {
        return this.pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public int getEpisodeCount() {
        return this.episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getLikesCount() {
        return this.likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getViewsCount() {
        return this.viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFavourite() {
        return this.isFavourite;
    }

    public void setFavourite(boolean favourite) {
        this.isFavourite = favourite;
    }

    public boolean isLiked() {
        return this.isLiked;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String toString() {
        return "ComicDetailObject{comicId='" + this.comicId + '\'' + ", title='" + this.title + '\'' + ", author='" + this.author + '\'' + ", description='" + this.description + '\'' + ", chineseTeam='" + this.chineseTeam + '\'' + ", creator=" + this.creator + ", thumb=" + this.thumb + ", categories=" + this.categories + ", tags=" + this.tags + ", commentsCount=" + this.commentsCount + ", pagesCount=" + this.pagesCount + ", episodeCount=" + this.episodeCount + ", likesCount=" + this.likesCount + ", viewsCount=" + this.viewsCount + ", finished=" + this.finished + ", isFavourite=" + this.isFavourite + ", isLiked=" + this.isLiked + ", updatedAt='" + this.updatedAt + '\'' + ", createdAt='" + this.createdAt + '\'' + '}';
    }
}
