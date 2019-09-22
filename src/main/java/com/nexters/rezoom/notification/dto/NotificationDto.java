package com.nexters.rezoom.notification.dto;

import com.nexters.rezoom.notification.domain.Notification;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by momentjin@gmail.com on 2019-09-03
 * Github : http://github.com/momentjin
 */
public class NotificationDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ListRes {

        private List<NotificationDto.ViewRes> notifications;

        public ListRes(List<Notification> notifications) {
            this.notifications = notifications.stream()
                    .map(ViewRes::new)
                    .collect(Collectors.toList());
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewRes {

        private long id;
        private long coverletterId;
        private long remainingDays;
        private long remainingHours;
        private boolean isChecked = false;
        private String contents;
        private LocalDateTime createDate;

        public ViewRes(Notification notification) {
            this.id = notification.getId();
            this.coverletterId = notification.getCoverletterId();
            this.remainingDays = notification.getRemainingDays();
            this.remainingHours = notification.getRemainingHours();
            this.isChecked = notification.isChecked();
            this.createDate = notification.getCreateDate();
            this.contents = notification.toString();
        }

    }
}
