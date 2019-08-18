package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class AnnouncementObject {
    @SerializedName("_id")
    String announcementId;
    String content;
    @SerializedName("created_at")
    String createdAt;
    ThumbnailObject thumb;
    String title;

    public AnnouncementObject(String announcementId, String title, String content, String createdAt, ThumbnailObject thumb) {
        this.announcementId = announcementId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.thumb = thumb;
    }

    public String getAnnouncementId() {
        return this.announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ThumbnailObject getThumb() {
        return this.thumb;
    }

    public void setThumb(ThumbnailObject thumb) {
        this.thumb = thumb;
    }
}
