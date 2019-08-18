package com.qiuchenly.comicx.ProductModules.Bika.responses;

public class CommentPostToTopResponse {
    boolean isTop;

    public CommentPostToTopResponse(boolean isTop) {
        this.isTop = isTop;
    }

    public boolean isTop() {
        return this.isTop;
    }

    public void setTop(boolean top) {
        this.isTop = top;
    }

    public String toString() {
        return "TopCommentResponse{isTop=" + this.isTop + '}';
    }
}
