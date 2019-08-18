package com.qiuchenly.comicx.ProductModules.Bika.responses;

public class ChatroomBlacklistObject {
    String userId;
    String username;

    public ChatroomBlacklistObject(String username, String userId) {
        this.username = username;
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString() {
        return "ChatroomBlacklistObject{username='" + this.username + '\'' + ", userId='" + this.userId + '\'' + '}';
    }
}
