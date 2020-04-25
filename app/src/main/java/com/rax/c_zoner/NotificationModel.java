package com.rax.c_zoner;

public class NotificationModel {
    String title;
    String link;

    public NotificationModel(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
