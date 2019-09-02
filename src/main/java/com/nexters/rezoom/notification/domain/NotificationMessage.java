package com.nexters.rezoom.notification.domain;

/**
 * Created by momentjin@gmail.com on 2019-09-06
 * Github : http://github.com/momentjin
 */
public class NotificationMessage {

    private String title;
    private String contents;

    public NotificationMessage(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
