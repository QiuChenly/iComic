package com.qiuchenly.comicx.ProductModules.Bika;

public class ChatroomListObject {
    String avatar;
    String description;
    String title;
    String url;

    public ChatroomListObject(String avatar, String title, String description, String url) {
        this.avatar = avatar;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return "ChatroomListObject{avatar='" + this.avatar + '\'' + ", title='" + this.title + '\'' + ", description='" + this.description + '\'' + ", url='" + this.url + '\'' + '}';
    }
}
