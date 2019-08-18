package com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.PostCommentResponse;

import com.google.gson.annotations.SerializedName;

public class PostCommentResponse {
    @SerializedName("childsCount")
    int childsCount;
    @SerializedName("_comic")
    String comicId;
    PostCommentData comment;
    @SerializedName("_id")
    String commentId;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("likesCount")
    int likesCount;
    @SerializedName("_parent")
    String parentId;

    public PostCommentResponse(PostCommentData comment, String parentId, String comicId, String commentId, String createdAt, int likesCount, int childsCount) {
        this.comment = comment;
        this.parentId = parentId;
        this.comicId = comicId;
        this.commentId = commentId;
        this.createdAt = createdAt;
        this.likesCount = likesCount;
        this.childsCount = childsCount;
    }

    public PostCommentData getComment() {
        return this.comment;
    }

    public void setComment(PostCommentData comment) {
        this.comment = comment;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getComicId() {
        return this.comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getCommentId() {
        return this.commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String toString() {
        return "PostCommentResponse{comment=" + this.comment + ", parentId='" + this.parentId + '\'' + ", comicId='" + this.comicId + '\'' + ", commentId='" + this.commentId + '\'' + ", createdAt='" + this.createdAt + '\'' + ", likesCount=" + this.likesCount + ", childsCount=" + this.childsCount + '}';
    }
}
