package com.nexters.rezoom.core.domain.notification.api.dto;

import com.nexters.rezoom.core.domain.notification.domain.Notification;
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

        private Long id;
        private Long coverletterId;
        private Long remainingDays;
        private Long remainingHours;
        private Boolean isChecked;
        private String contents;
        private LocalDateTime createDate;

        public ViewRes(Notification notification) {
            this.id = notification.getId();
            this.coverletterId = notification.getCoverletterId();
            this.remainingDays = notification.getRemainingDays();
            this.remainingHours = notification.getRemainingHours();
            this.isChecked = notification.isChecked();
            this.createDate = notification.getCreatedAt();
            this.contents = notification.toString();
        }

    }
}
