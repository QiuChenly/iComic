package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class CommentObject {
    @SerializedName("commentsCount")
    int childsCount;
    @SerializedName("_comic")
    String comicId;
    @SerializedName("_id")
    String commentId;
    String content;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("_game")
    String gameId;
    boolean hide;
    boolean isLiked;
    boolean isTop;
    int likesCount;
    @SerializedName("_user")
    UserProfileObject user;

    public CommentObject(String commentId, String content, UserProfileObject user, String comicId, String gameId, String createdAt, int likesCount, int childsCount, boolean isLiked, boolean hide, boolean isTop) {
        this.commentId = commentId;
        this.content = content;
        this.user = user;
        this.comicId = comicId;
        this.gameId = gameId;
        this.createdAt = createdAt;
        this.likesCount = likesCount;
        this.childsCount = childsCount;
        this.isLiked = isLiked;
        this.hide = hide;
        this.isTop = isTop;
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

    public UserProfileObject getUser() {
        return this.user;
    }

    public void setUser(UserProfileObject user) {
        this.user = user;
    }

    public String getComicId() {
        return this.comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getGameId() {
        return this.gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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

    public boolean isTop() {
        return this.isTop;
    }

    public void setTop(boolean top) {
        this.isTop = top;
    }

    public String toString() {
        return "CommentObject{commentId='" + this.commentId + '\'' + ", content='" + this.content + '\'' + ", user=" + this.user + ", comicId='" + this.comicId + '\'' + ", gameId='" + this.gameId + '\'' + ", createdAt='" + this.createdAt + '\'' + ", likesCount=" + this.likesCount + ", childsCount=" + this.childsCount + ", isLiked=" + this.isLiked + ", hide=" + this.hide + ", isTop=" + this.isTop + '}';
    }
}
