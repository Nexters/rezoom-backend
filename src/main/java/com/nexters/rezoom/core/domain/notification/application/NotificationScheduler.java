package com.nexters.rezoom.core.domain.notification.application;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by momentjin@gmail.com on 2019-09-04
 * Github : http://github.com/momentjin
 */

@Component
public class NotificationScheduler {

    private final NotificationService notificationService;

    public NotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // 매일 07시, 자기소개서 마감 알림 데이터 미리 생성
    @Scheduled(cron = "0 0 7 * * *")
    public void createNotifications() {
        notificationService.createNotifications();
    }

    // 매일 08시, 알림 전송 (이메일 등)
    @Scheduled(cron = "0 0 8 * * *")
    public void sendNotifications() {
        notificationService.sendNotifications();
    }
}
