package com.qiuchenly.comicx.ProductModules.Bika.responses;


import com.qiuchenly.comicx.ProductModules.Bika.LeaderboardKnightObject;

import java.util.ArrayList;

public class LeaderboardKnightResponse {
    ArrayList<LeaderboardKnightObject> users;

    public LeaderboardKnightResponse(ArrayList<LeaderboardKnightObject> users) {
        this.users = users;
    }

    public ArrayList<LeaderboardKnightObject> getUsers() {
        return this.users;
    }

    public void setUsers(ArrayList<LeaderboardKnightObject> users) {
        this.users = users;
    }

    public String toString() {
        return "LeaderboardKnightResponse{users=" + this.users + '}';
    }
}
