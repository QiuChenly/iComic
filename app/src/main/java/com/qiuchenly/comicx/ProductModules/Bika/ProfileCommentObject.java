package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class ProfileCommentObject {
    @SerializedName("commentsCount")
    int childsCount;
    @SerializedName("_comic")
    CommentComicIdTitleObject commentComicIdTitleObject;
    @SerializedName("_game")
    CommentGameIdTitleObject commentGameIdTitleObject;
    @SerializedName("_id")
    String commentId;
    String content;
    @SerializedName("created_at")
    String createdAt;
    boolean hide;
    boolean isLiked;
    int likesCount;
    @SerializedName("_user")
    String user;

    public ProfileCommentObject(String commentId, String content, String user, CommentComicIdTitleObject commentComicIdTitleObject, CommentGameIdTitleObject commentGameIdTitleObject, String createdAt, int likesCount, int childsCount, boolean isLiked, boolean hide) {
        this.commentId = commentId;
        this.content = content;
        this.user = user;
        this.commentComicIdTitleObject = commentComicIdTitleObject;
        this.commentGameIdTitleObject = commentGameIdTitleObject;
        this.createdAt = createdAt;
        this.likesCount = likesCount;
        this.childsCount = childsCount;
        this.isLiked = isLiked;
        this.hide = hide;
    }

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

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public CommentComicIdTitleObject getCommentComicIdTitleObject() {
        return this.commentComicIdTitleObject;
    }

    public void setCommentComicIdTitleObject(CommentComicIdTitleObject commentComicIdTitleObject) {
        this.commentComicIdTitleObject = commentComicIdTitleObject;
    }

    public CommentGameIdTitleObject getCommentGameIdTitleObject() {
        return this.commentGameIdTitleObject;
    }

    public void setCommentGameIdTitleObject(CommentGameIdTitleObject commentGameIdTitleObject) {
        this.commentGameIdTitleObject = commentGameIdTitleObject;
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

    public boolean isHide() {
        return this.hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public String toString() {
        return "ProfileCommentObject{commentId='" + this.commentId + '\'' + ", content='" + this.content + '\'' + ", user='" + this.user + '\'' + ", commentComicIdTitleObject='" + this.commentComicIdTitleObject + '\'' + ", commentGameIdTitleObject='" + this.commentGameIdTitleObject + '\'' + ", createdAt='" + this.createdAt + '\'' + ", likesCount='" + this.likesCount + '\'' + ", childsCount='" + this.childsCount + '\'' + ", isLiked='" + this.isLiked + '\'' + ", hide='" + this.hide + '\'' + '}';
    }
}
