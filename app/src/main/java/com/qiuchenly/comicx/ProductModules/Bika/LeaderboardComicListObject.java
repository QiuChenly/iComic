package com.qiuchenly.comicx.ProductModules.Bika;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LeaderboardComicListObject implements Parcelable {
    public static final Creator<LeaderboardComicListObject> CREATOR = new Creator<LeaderboardComicListObject>() {
        public LeaderboardComicListObject createFromParcel(Parcel source) {
            return new LeaderboardComicListObject(source);
        }

        public LeaderboardComicListObject[] newArray(int size) {
            return new LeaderboardComicListObject[size];
        }
    };
    String author;
    ArrayList<String> categories;
    @SerializedName("_id")
    String comicId;
    @SerializedName("epsCount")
    int episodeCount;
    boolean finished;
    int leaderboardCount;
    int likesCount;
    int pagesCount;
    ThumbnailObject thumb;
    String title;
    int viewsCount;

    public LeaderboardComicListObject(String comicId, String title, String author, int likesCount, int pagesCount, int episodeCount, int viewsCount, int leaderboardCount, boolean finished, ArrayList<String> categories, ThumbnailObject thumb) {
        this.comicId = comicId;
        this.title = title;
        this.author = author;
        this.likesCount = likesCount;
        this.pagesCount = pagesCount;
        this.episodeCount = episodeCount;
        this.viewsCount = viewsCount;
        this.leaderboardCount = leaderboardCount;
        this.finished = finished;
        this.categories = categories;
        this.thumb = thumb;
    }

    protected LeaderboardComicListObject(Parcel in) {
        this.comicId = in.readString();
        this.title = in.readString();
        this.author = in.readString();
        this.likesCount = in.readInt();
        this.pagesCount = in.readInt();
        this.episodeCount = in.readInt();
        this.viewsCount = in.readInt();
        this.leaderboardCount = in.readInt();
        this.finished = in.readByte() != (byte) 0;
        this.categories = in.createStringArrayList();
        this.thumb = in.readParcelable(ThumbnailObject.class.getClassLoader());
    }

    public String getComicId() {
        return this.comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLikesCount() {
        return this.likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getPagesCount() {
        return this.pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public int getEpisodeCount() {
        return this.episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getViewsCount() {
        return this.viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public int getLeaderboardCount() {
        return this.leaderboardCount;
    }

    public void setLeaderboardCount(int leaderboardCount) {
        this.leaderboardCount = leaderboardCount;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public ArrayList<String> getCategories() {
        return this.categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ThumbnailObject getThumb() {
        return this.thumb;
    }

    public void setThumb(ThumbnailObject thumb) {
        this.thumb = thumb;
    }

    public String toString() {
        return "LeaderboardComicListObject{comicId='" + this.comicId + '\'' + ", title='" + this.title + '\'' + ", author='" + this.author + '\'' + ", likesCount=" + this.likesCount + ", pagesCount=" + this.pagesCount + ", episodeCount=" + this.episodeCount + ", viewsCount=" + this.viewsCount + ", leaderboardCount=" + this.leaderboardCount + ", finished=" + this.finished + ", categories=" + this.categories + ", thumb=" + this.thumb + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.comicId);
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeInt(this.likesCount);
        dest.writeInt(this.pagesCount);
        dest.writeInt(this.episodeCount);
        dest.writeInt(this.viewsCount);
        dest.writeInt(this.leaderboardCount);
        dest.writeByte(this.finished ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.categories);
        dest.writeParcelable(this.thumb, flags);
    }
}
