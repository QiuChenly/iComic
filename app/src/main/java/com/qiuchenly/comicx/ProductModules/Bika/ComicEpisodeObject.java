package com.qiuchenly.comicx.ProductModules.Bika;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class ComicEpisodeObject implements Parcelable {
    public static final Creator<ComicEpisodeObject> CREATOR = new Creator<ComicEpisodeObject>() {
        public ComicEpisodeObject createFromParcel(Parcel source) {
            return new ComicEpisodeObject(source);
        }

        public ComicEpisodeObject[] newArray(int size) {
            return new ComicEpisodeObject[size];
        }
    };
    @SerializedName("_id")
    String episodeId;
    int order;
    boolean selected;
    int status;
    String title;
    @SerializedName("updated_at")
    String updatedAt;

    public ComicEpisodeObject(String episodeId, String title, int order, String updatedAt) {
        this.episodeId = episodeId;
        this.title = title;
        this.order = order;
        this.updatedAt = updatedAt;
        this.status = 0;
        this.selected = false;
    }

    protected ComicEpisodeObject(Parcel in) {
        this.episodeId = in.readString();
        this.title = in.readString();
        this.order = in.readInt();
        this.updatedAt = in.readString();
        this.status = in.readInt();
        this.selected = in.readByte() != (byte) 0;
    }

    public String getEpisodeId() {
        return this.episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String toString() {
        return "ComicEpisodeObject{episodeId='" + this.episodeId + '\'' + ", title='" + this.title + '\'' + ", order=" + this.order + ", updatedAt='" + this.updatedAt + '\'' + ", status=" + this.status + ", selected=" + this.selected + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.episodeId);
        dest.writeString(this.title);
        dest.writeInt(this.order);
        dest.writeString(this.updatedAt);
        dest.writeInt(this.status);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }
}
