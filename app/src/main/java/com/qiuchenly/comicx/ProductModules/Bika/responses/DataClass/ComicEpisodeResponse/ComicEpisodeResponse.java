package com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse;

public class ComicEpisodeResponse {
    ComicEpisodeData eps;

    public ComicEpisodeResponse(ComicEpisodeData eps) {
        this.eps = eps;
    }

    public ComicEpisodeData getEps() {
        return this.eps;
    }

    public void setEps(ComicEpisodeData eps) {
        this.eps = eps;
    }

    public String toString() {
        return "ComicEpisodeResponse{eps=" + this.eps + '}';
    }
}
