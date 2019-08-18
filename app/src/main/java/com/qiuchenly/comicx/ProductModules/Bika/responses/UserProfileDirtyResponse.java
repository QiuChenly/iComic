package com.qiuchenly.comicx.ProductModules.Bika.responses;

public class UserProfileDirtyResponse {
    boolean dirty;

    public UserProfileDirtyResponse(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public String toString() {
        return "UserProfileDirtyResponse{dirty=" + this.dirty + '}';
    }
}
