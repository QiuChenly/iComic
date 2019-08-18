package com.qiuchenly.comicx.ProductModules.Bika;

public class ChatroomToObject {
    public String name;
    public String unique_id;
    public String user_id;

    public ChatroomToObject(String name, String unique_id, String user_id) {
        this.name = name;
        this.unique_id = unique_id;
        this.user_id = user_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnique_id() {
        return this.unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getUserId() {
        return this.user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String toString() {
        return "ChatroomToObject{name='" + this.name + '\'' + ", unique_id='" + this.unique_id + '\'' + ", user_id='" + this.user_id + '\'' + '}';
    }
}
