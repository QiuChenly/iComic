package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class BaseCommentObject {
    @SerializedName("commentsCount")
    int childsCount;
    @SerializedName("_id")
    String commentId;
    String content;
    @SerializedName("created_at")
    String createdAt;
    boolean isLiked;
    int likesCount;
    @SerializedName("_user")
    UserBasicObject user;

    public String getCommentId() {
        return this.commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBasicObject getUser() {
        return this.user;
    }

    public void setUser(UserBasicObject user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getLikesCount() {
        return this.likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getChildsCount() {
        return this.childsCount;
    }

    public void setChildsCount(int childsCount) {
        this.childsCount = childsCount;
    }

    public boolean isLiked() {
        return this.isLiked;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
    }
}
