package com.qiuchenly.comicx.ProductModules.Bika;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserProfileObject implements Parcelable {
    public static final Creator<UserProfileObject> CREATOR = new Creator<UserProfileObject>() {
        public UserProfileObject createFromParcel(Parcel source) {
            return new UserProfileObject(source);
        }

        public UserProfileObject[] newArray(int size) {
            return new UserProfileObject[size];
        }
    };
    @SerializedName("activation_date")
    String activationDate;
    ThumbnailObject avatar;
    String birthday;
    String character;
    ArrayList<String> characters;
    String email;
    int exp;
    String gender;
    boolean isPunched;
    int level;
    String name;
    String role;
    String slogan;
    String title;
    @SerializedName("_id")
    String userId;
    boolean verified;

    public UserProfileObject(String userId, String email, String name, String title, String birthday, String gender, String slogan, String role, String character, ArrayList<String> characters, String activationDate, int exp, int level, boolean isPunched, boolean verified, ThumbnailObject avatar) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.title = title;
        this.birthday = birthday;
        this.gender = gender;
        this.slogan = slogan;
        this.role = role;
        this.character = character;
        this.characters = characters;
        this.activationDate = activationDate;
        this.exp = exp;
        this.level = level;
        this.isPunched = isPunched;
        this.verified = verified;
        this.avatar = avatar;
    }

    protected UserProfileObject(Parcel in) {
        boolean z;
        boolean z2 = true;
        this.userId = in.readString();
        this.email = in.readString();
        this.name = in.readString();
        this.title = in.readString();
        this.birthday = in.readString();
        this.gender = in.readString();
        this.slogan = in.readString();
        this.role = in.readString();
        this.character = in.readString();
        this.characters = in.createStringArrayList();
        this.activationDate = in.readString();
        this.exp = in.readInt();
        this.level = in.readInt();
        z = in.readByte() != (byte) 0;
        this.isPunched = z;
        if (in.readByte() == (byte) 0) {
            z2 = false;
        }
        this.verified = z2;
        this.avatar = in.readParcelable(ThumbnailObject.class.getClassLoader());
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSlogan() {
        return this.slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCharacter() {
        return this.character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public ArrayList<String> getCharacters() {
        return this.characters;
    }

    public void setCharacters(ArrayList<String> characters) {
        this.characters = characters;
    }

    public String[] getCharactersStringArray() {
        if (this.characters == null || this.characters.size() <= 0) {
            return null;
        }
        String[] strArr = new String[this.characters.size()];
        for (int i = 0; i < this.characters.size(); i++) {
            strArr[i] = this.characters.get(i);
        }
        return strArr;
    }

    public String getActivationDate() {
        return this.activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
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

    public boolean isPunched() {
        return this.isPunched;
    }

    public void setPunched(boolean punched) {
        this.isPunched = punched;
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
        return "UserProfileObject{userId='" + this.userId + '\'' + ", email='" + this.email + '\'' + ", name='" + this.name + '\'' + ", title='" + this.title + '\'' + ", birthday='" + this.birthday + '\'' + ", gender='" + this.gender + '\'' + ", slogan='" + this.slogan + '\'' + ", role='" + this.role + '\'' + ", character='" + this.character + '\'' + ", characters='" + this.characters + '\'' + ", activationDate='" + this.activationDate + '\'' + ", exp=" + this.exp + ", level=" + this.level + ", isPunched=" + this.isPunched + ", verified=" + this.verified + ", avatar=" + this.avatar + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2 = (byte) 1;
        dest.writeString(this.userId);
        dest.writeString(this.email);
        dest.writeString(this.name);
        dest.writeString(this.title);
        dest.writeString(this.birthday);
        dest.writeString(this.gender);
        dest.writeString(this.slogan);
        dest.writeString(this.role);
        dest.writeString(this.character);
        dest.writeStringList(this.characters);
        dest.writeString(this.activationDate);
        dest.writeInt(this.exp);
        dest.writeInt(this.level);
        if (this.isPunched) {
            b = (byte) 1;
        } else {
            b = (byte) 0;
        }
        dest.writeByte(b);
        if (!this.verified) {
            b2 = (byte) 0;
        }
        dest.writeByte(b2);
        dest.writeParcelable(this.avatar, flags);
    }
}
