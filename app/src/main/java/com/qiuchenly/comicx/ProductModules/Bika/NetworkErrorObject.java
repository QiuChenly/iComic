package com.qiuchenly.comicx.ProductModules.Bika;

public class NetworkErrorObject {
    int code;
    String detail;
    String error;
    String message;

    public NetworkErrorObject(int code, String error, String message, String detail) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.detail = detail;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String toString() {
        return "NetworkErrorObject{code=" + this.code + ", error='" + this.error + '\'' + ", message='" + this.message + '\'' + ", detail='" + this.detail + '\'' + '}';
    }
}
