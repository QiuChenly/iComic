package com.qiuchenly.comicx.ProductModules.Bika.responses;

public class PunchInObject {
    String punchInLastDay;
    String status;

    public PunchInObject(String status, String punchInLastDay) {
        this.status = status;
        this.punchInLastDay = punchInLastDay;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPunchInLastDay() {
        return this.punchInLastDay;
    }

    public void setPunchInLastDay(String punchInLastDay) {
        this.punchInLastDay = punchInLastDay;
    }

    public String toString() {
        return "PunchInObject{status='" + this.status + '\'' + ", punchInLastDay='" + this.punchInLastDay + '\'' + '}';
    }
}
