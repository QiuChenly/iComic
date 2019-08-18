package com.qiuchenly.comicx.ProductModules.Bika;

public class ApkObject {
    String fileServer;
    String originalName;
    String path;

    public ApkObject(String originalName, String path, String fileServer) {
        this.originalName = originalName;
        this.path = path;
        this.fileServer = fileServer;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileServer() {
        return this.fileServer;
    }

    public void setFileServer(String fileServer) {
        this.fileServer = fileServer;
    }

    public String toString() {
        return "ApkObject{originalName='" + this.originalName + '\'' + ", path='" + this.path + '\'' + ", fileServer='" + this.fileServer + '\'' + '}';
    }
}
