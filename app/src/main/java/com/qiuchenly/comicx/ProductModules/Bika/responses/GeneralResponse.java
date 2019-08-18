package com.qiuchenly.comicx.ProductModules.Bika.responses;

import androidx.annotation.Nullable;

public class GeneralResponse<DataClass> {
    public int code;
    @Nullable
    public DataClass data;
    public String message;

    public GeneralResponse(DataClass data) {
        this.data = data;
    }

    public String toString() {
        return "GeneralResponse{code=" + this.code + ", message='" + this.message + '\'' + ", data=" + this.data + '}';
    }
}
