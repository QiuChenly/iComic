package com.qiuchenly.comicx.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LeaderboardKnightObject {
    ThumbnailObject avatar;
    String character;
    ArrayList<String> characters;
    int comicsUploaded;
    int exp;
    String gender;
    @SerializedName("_id")
    String leaderboardKnightId;
    int level;
    String name;
    boolean verified;

    public LeaderboardKnightObject(String leaderboardKnightId, String name, String gender, String character, ThumbnailObject avatar, ArrayList<String> characters, int level, int exp, int comicsUploaded, boolean verified) {
        this.leaderboardKnightId = leaderboardKnightId;
        this.name = name;
        this.gender = gender;
        this.character = character;
        this.avatar = avatar;
        this.characters = characters;
        this.level = level;
        this.exp = exp;
        this.comicsUploaded = comicsUploaded;
        this.verified = verified;
    }

    public String getLeaderboardKnightId() {
        return this.leaderboardKnightId;
    }

    public void setLeaderboardKnightId(String leaderboardKnightId) {
        this.leaderboardKnightId = leaderboardKnightId;
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

    public ThumbnailObject getAvatar() {
        return this.avatar;
    }

    public void setAvatar(ThumbnailObject avatar) {
        this.avatar = avatar;
    }

    public ArrayList<String> getCharacters() {
        return this.characters;
    }

    public void setCharacters(ArrayList<String> characters) {
        this.characters = characters;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return this.exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getComicsUploaded() {
        return this.comicsUploaded;
    }

    public void setComicsUploaded(int comicsUploaded) {
        this.comicsUploaded = comicsUploaded;
    }

    public boolean isVerified() {
        return this.verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String toString() {
        return "LeaderboardKnightObject{leaderboardKnightId='" + this.leaderboardKnightId + '\'' + ", name='" + this.name + '\'' + ", gender='" + this.gender + '\'' + ", character='" + this.character + '\'' + ", avatar=" + this.avatar + ", characters=" + this.characters + ", level=" + this.level + ", exp=" + this.exp + ", comicsUploaded=" + this.comicsUploaded + ", verified=" + this.verified + '}';
    }
}
