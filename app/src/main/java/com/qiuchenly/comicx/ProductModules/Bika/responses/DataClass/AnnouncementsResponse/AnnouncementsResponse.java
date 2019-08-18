package com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.AnnouncementsResponse;

public class AnnouncementsResponse {
    AnnouncementsData announcements;

    public AnnouncementsResponse(AnnouncementsData announcements) {
        this.announcements = announcements;
    }

    public AnnouncementsData getAnnouncements() {
        return this.announcements;
    }

    public void setAnnouncements(AnnouncementsData announcements) {
        this.announcements = announcements;
    }
}
