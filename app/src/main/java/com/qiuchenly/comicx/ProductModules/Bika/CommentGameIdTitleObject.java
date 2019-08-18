package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class CommentGameIdTitleObject {
    @SerializedName("_id")
    String gameId;
    @SerializedName("title")
    String title;

    public CommentGameIdTitleObject(String gameId, String title) {
        this.gameId = gameId;
        this.title = title;
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

    public String toString() {
        return "CommentGameObject{gameId='" + this.gameId + '\'' + ", title='" + this.title + '\'' + '}';
    }
}
