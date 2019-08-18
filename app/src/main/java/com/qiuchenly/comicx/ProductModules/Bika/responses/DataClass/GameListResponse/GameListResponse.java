package com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.GameListResponse;

public class GameListResponse {
    GameListData games;

    public GameListResponse(GameListData games) {
        this.games = games;
    }

    public GameListData getGames() {
        return this.games;
    }

    public void setGames(GameListData games) {
        this.games = games;
    }

    public String toString() {
        return "GameListResponse{games=" + this.games + '}';
    }
}
