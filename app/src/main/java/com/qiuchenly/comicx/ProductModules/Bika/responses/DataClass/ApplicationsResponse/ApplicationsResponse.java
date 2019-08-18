package com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ApplicationsResponse;

public class ApplicationsResponse {
    ApplicationsData applications;

    public ApplicationsResponse(ApplicationsData applications) {
        this.applications = applications;
    }

    public ApplicationsData getApplications() {
        return this.applications;
    }

    public void setApplications(ApplicationsData applications) {
        this.applications = applications;
    }

    public String toString() {
        return "ApplicationsResponse{applications=" + this.applications + '}';
    }
}
