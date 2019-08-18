package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommentWithReplyObject {
    ArrayList<CommentObject> arrayList;
    @SerializedName("commentsCount")
    int childsCount;
    @SerializedName("_comic")
    CommentComicIdTitleObject comicId;
    @SerializedName("_id")
    String commentId;
    String content;
    @SerializedName("created_at")
    String createdAt;
    int currentPage;
    @SerializedName("_game")
    CommentGameIdTitleObject gameId;
    boolean hide;
    boolean isLiked;
    boolean isTop;
    int likesCount;
    String title;
    int totalPage;
    @SerializedName("_user")
    UserProfileObject user;

    public CommentWithReplyObject() {
        this.currentPage = 0;
        this.totalPage = 1;
    }

    public CommentWithReplyObject(String commentId, String content, UserProfileObject user, CommentComicIdTitleObject comicId, CommentGameIdTitleObject gameId, String title, String createdAt, int likesCount, int childsCount, boolean isLiked, boolean hide, boolean isTop) {
        this.commentId = commentId;
        this.content = content;
        this.user = user;
        this.comicId = comicId;
        this.gameId = gameId;
        this.title = title;
        this.createdAt = createdAt;
        this.likesCount = likesCount;
        this.childsCount = childsCount;
        this.isLiked = isLiked;
        this.hide = hide;
        this.isTop = isTop;
        this.currentPage = 0;
        this.totalPage = 1;
    }

    public CommentWithReplyObject(CommentObject commentObject) {
        this.commentId = commentObject.commentId;
        this.content = commentObject.content;
        this.user = commentObject.user;
        this.comicId = new CommentComicIdTitleObject(commentObject.getComicId(), "");
        this.gameId = new CommentGameIdTitleObject(commentObject.getGameId(), "");
        this.createdAt = commentObject.createdAt;
        this.likesCount = commentObject.likesCount;
        this.childsCount = commentObject.childsCount;
        this.isLiked = commentObject.isLiked;
        this.hide = commentObject.hide;
        this.isTop = commentObject.isTop;
        this.currentPage = 0;
        this.totalPage = 1;
    }

    public CommentWithReplyObject(ProfileCommentObject profileCommentObject, UserProfileObject userBasicObject) {
        this.commentId = profileCommentObject.commentId;
        this.content = profileCommentObject.content;
        this.user = userBasicObject;
        this.comicId = profileCommentObject.getCommentComicIdTitleObject();
        this.gameId = profileCommentObject.getCommentGameIdTitleObject();
        this.createdAt = profileCommentObject.createdAt;
        this.likesCount = profileCommentObject.likesCount;
        this.childsCount = profileCommentObject.childsCount;
        this.isLiked = profileCommentObject.isLiked;
        this.hide = profileCommentObject.hide;
        this.isTop = false;
        this.currentPage = 0;
        this.totalPage = 1;
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

    public CommentComicIdTitleObject getComicId() {
        return this.comicId;
    }

    public void setComicId(CommentComicIdTitleObject comicId) {
        this.comicId = comicId;
    }

    public CommentGameIdTitleObject getGameId() {
        return this.gameId;
    }

    public void setGameId(CommentGameIdTitleObject gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<CommentObject> getArrayList() {
        return this.arrayList;
    }

    public void setArrayList(ArrayList<CommentObject> arrayList) {
        this.arrayList = arrayList;
    }

    public String toString() {
        return "CommentWithReplyObject{commentId='" + this.commentId + '\'' + ", content='" + this.content + '\'' + ", user='" + this.user + '\'' + ", comicId='" + this.comicId + '\'' + ", gameId='" + this.gameId + '\'' + ", createdAt='" + this.createdAt + '\'' + ", likesCount='" + this.likesCount + '\'' + ", childsCount='" + this.childsCount + '\'' + ", currentPage='" + this.currentPage + '\'' + ", isLiked='" + this.isLiked + '\'' + ", totalPage='" + this.totalPage + '\'' + ", arrayList='" + this.arrayList + '\'' + ", hide='" + this.hide + '\'' + ", isTop='" + this.isTop + '\'' + '}';
    }
}
