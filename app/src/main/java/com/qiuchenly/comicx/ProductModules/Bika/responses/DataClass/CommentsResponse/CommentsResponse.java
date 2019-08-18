package com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.CommentsResponse;


import com.qiuchenly.comicx.ProductModules.Bika.CommentObject;

import java.util.ArrayList;

public class CommentsResponse {
    CommentsData comments;
    ArrayList<CommentObject> topComments;

    public CommentsResponse(CommentsData comments, ArrayList<CommentObject> topComments) {
        this.comments = comments;
        this.topComments = topComments;
    }

    public CommentsData getComments() {
        return this.comments;
    }

    public void setComments(CommentsData comments) {
        this.comments = comments;
    }

    public ArrayList<CommentObject> getTopComments() {
        return this.topComments;
    }

    public void setTopComments(ArrayList<CommentObject> topComments) {
        this.topComments = topComments;
    }

    public String toString() {
        return "CommentsResponse{comments=" + this.comments + ", topComments=" + this.topComments + '}';
    }
}
