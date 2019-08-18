package com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.GameDetailResponse;


import com.qiuchenly.comicx.ProductModules.Bika.GameDetailObject;

public class GameDetailResponse {
    GameDetailObject game;

    public GameDetailResponse(GameDetailObject game) {
        this.game = game;
    }

    public GameDetailObject getGame() {
        return this.game;
    }

    public void setGame(GameDetailObject game) {
        this.game = game;
    }

    public String toString() {
        return "GameDetailResponse{game=" + this.game + '}';
    }
}
