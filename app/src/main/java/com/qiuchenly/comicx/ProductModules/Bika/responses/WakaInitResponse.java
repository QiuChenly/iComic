package com.qiuchenly.comicx.ProductModules.Bika.responses;

import java.util.ArrayList;

public class WakaInitResponse {
    ArrayList<String> addresses;
    String status;
    String waka;

    public WakaInitResponse(String status, ArrayList<String> addresses, String waka) {
        this.status = status;
        this.addresses = addresses;
        this.waka = waka;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(ArrayList<String> addresses) {
        this.addresses = addresses;
    }

    public String getWaka() {
        return this.waka;
    }

    public void setWaka(String waka) {
        this.waka = waka;
    }

    public String toString() {
        return "WakaInitResponse{status='" + this.status + '\'' + ", addresses=" + this.addresses + ", waka='" + this.waka + '\'' + '}';
    }
}
