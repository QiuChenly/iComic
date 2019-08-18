package com.qiuchenly.comicx.ProductModules.Bika;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class UserBasicObject implements Parcelable {
    public static final Creator<UserBasicObject> CREATOR = new Creator<UserBasicObject>() {
        public UserBasicObject createFromParcel(Parcel source) {
            return new UserBasicObject(source);
        }

        public UserBasicObject[] newArray(int size) {
            return new UserBasicObject[size];
        }
    };
    ThumbnailObject avatar;
    String character;
    int exp;
    String gender;
    int level;
    String name;
    @SerializedName("_id")
    String userId;
    boolean verified;

    public UserBasicObject(String userId, String name, String gender, String character, int exp, int level, boolean verified, ThumbnailObject avatar) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.character = character;
        this.exp = exp;
        this.level = level;
        this.verified = verified;
        this.avatar = avatar;
    }

    public UserBasicObject(UserProfileObject userProfileObject) {
        this.userId = userProfileObject.getUserId();
        this.name = userProfileObject.getName();
        this.gender = userProfileObject.getGender();
        this.character = userProfileObject.getCharacter();
        this.exp = userProfileObject.getExp();
        this.level = userProfileObject.getLevel();
        this.verified = userProfileObject.isVerified();
        this.avatar = userProfileObject.getAvatar();
    }

    protected UserBasicObject(Parcel in) {
        this.userId = in.readString();
        this.name = in.readString();
        this.gender = in.readString();
        this.character = in.readString();
        this.exp = in.readInt();
        this.level = in.readInt();
        this.verified = in.readByte() != (byte) 0;
        this.avatar = in.readParcelable(ThumbnailObject.class.getClassLoader());
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCharacter() {
        return this.character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getExp() {
        return this.exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isVerified() {
        return this.verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public ThumbnailObject getAvatar() {
        return this.avatar;
    }

    public void setAvatar(ThumbnailObject avatar) {
        this.avatar = avatar;
    }

    public String toString() {
        return "UserBasicObject{userId='" + this.userId + '\'' + ", name='" + this.name + '\'' + ", gender='" + this.gender + '\'' + ", character='" + this.character + '\'' + ", exp=" + this.exp + ", level=" + this.level + ", verified=" + this.verified + ", avatar=" + this.avatar + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.gender);
        dest.writeString(this.character);
        dest.writeInt(this.exp);
        dest.writeInt(this.level);
        dest.writeByte(this.verified ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.avatar, flags);
    }
}
