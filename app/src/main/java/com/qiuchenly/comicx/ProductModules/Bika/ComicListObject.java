package com.qiuchenly.comicx.ProductModules.Bika;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ComicListObject implements Parcelable {
    public static final Creator<ComicListObject> CREATOR = new Creator<ComicListObject>() {
        public ComicListObject createFromParcel(Parcel in) {
            return new ComicListObject(in);
        }

        public ComicListObject[] newArray(int size) {
            return new ComicListObject[size];
        }
    };
    String author;
    ArrayList<String> categories;
    @SerializedName("_id")
    String comicId;
    @SerializedName("epsCount")
    int episodeCount;
    boolean finished;
    int likesCount;
    int pagesCount;
    ThumbnailObject thumb;
    String title;

    protected ComicListObject(Parcel in) {
        this.comicId = in.readString();
        this.title = in.readString();
        this.author = in.readString();
        this.likesCount = in.readInt();
        this.pagesCount = in.readInt();
        this.episodeCount = in.readInt();
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
        return "ComicListObject{comicId='" + this.comicId + '\'' + ", title='" + this.title + '\'' + ", author='" + this.author + '\'' + ", likesCount=" + this.likesCount + ", pagesCount=" + this.pagesCount + ", episodeCount=" + this.episodeCount + ", finished=" + this.finished + ", categories=" + this.categories + ", thumb=" + this.thumb + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.comicId);
        parcel.writeString(this.title);
        parcel.writeString(this.author);
        parcel.writeInt(this.likesCount);
        parcel.writeInt(this.pagesCount);
        parcel.writeInt(this.episodeCount);
        parcel.writeByte((byte) (this.finished ? 1 : 0));
        parcel.writeStringList(this.categories);
        parcel.writeParcelable(this.thumb, i);
    }
}
