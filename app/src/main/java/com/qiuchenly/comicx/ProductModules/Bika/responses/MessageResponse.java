package com.qiuchenly.comicx.ProductModules.Bika.responses;

public class MessageResponse {
    String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "MessageResponse{message='" + this.message + '\'' + '}';
    }
}
