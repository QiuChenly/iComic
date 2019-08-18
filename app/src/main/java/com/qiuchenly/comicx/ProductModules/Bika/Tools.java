package com.qiuchenly.comicx.ProductModules.Bika;

import com.qiuchenly.comicx.Core.Comic;

import java.util.Objects;

public class Tools {
    public static String getThumbnailImagePath(ThumbnailObject thumbnailObject) {
        if (thumbnailObject == null) {
            return null;
        }
        if (thumbnailObject.getFileServer().equalsIgnoreCase("http://lorempixel.com")) {
            return thumbnailObject.getFileServer() + thumbnailObject.getPath();
        }
        if (Comic.INSTANCE == null || PreferenceHelper.getImageStorage(Objects.requireNonNull(Comic.INSTANCE.getContext())) == null) {
            return thumbnailObject.getFileServer() + "/static/" + thumbnailObject.getPath();
        }
        return PreferenceHelper.getImageStorage(Objects.requireNonNull(Comic.INSTANCE.getContext())) + thumbnailObject.getPath();
    }
}
