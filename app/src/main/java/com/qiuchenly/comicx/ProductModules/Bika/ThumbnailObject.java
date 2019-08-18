package com.qiuchenly.comicx.ProductModules.Bika;

import android.os.Parcel;
import android.os.Parcelable;

public class ThumbnailObject implements Parcelable {
    public static final Creator<ThumbnailObject> CREATOR = new Creator<ThumbnailObject>() {
        public ThumbnailObject createFromParcel(Parcel in) {
            return new ThumbnailObject(in);
        }

        public ThumbnailObject[] newArray(int size) {
            return new ThumbnailObject[size];
        }
    };
    String fileServer;
    String originalName;
    String path;

    public ThumbnailObject(String fileServer, String path, String originalName) {
        this.fileServer = fileServer;
        this.path = path;
        this.originalName = originalName;
    }

    protected ThumbnailObject(Parcel in) {
        this.fileServer = in.readString();
        this.path = in.readString();
        this.originalName = in.readString();
    }

    public String getFileServer() {
        return this.fileServer;
    }

    public void setFileServer(String fileServer) {
        this.fileServer = fileServer;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String toString() {
        return "ThumbnailObject{fileServer='" + this.fileServer + '\'' + ", path='" + this.path + '\'' + ", originalName='" + this.originalName + '\'' + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fileServer);
        parcel.writeString(this.path);
        parcel.writeString(this.originalName);
    }
}
