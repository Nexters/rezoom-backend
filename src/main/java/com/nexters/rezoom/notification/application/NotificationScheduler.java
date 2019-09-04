package com.nexters.rezoom.notification.application;

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

    // cron =  초, 분, 시, 일, 월, 요일 (요일은 생략 가능)
    // 우선 심플하게 매일 한 번, 오전 7시로 설정함
    @Scheduled(cron = "0 0 7 * * *")
    public void createNotifications() {
        notificationService.createNotifications();
    }

    // 매일 오전 8시
    @Scheduled(cron = "0 0 8 * * *")
    public void sendNotifications() {

        /*
         * List members = findAllMember();
         * for member : members
         *      Set<NotiType> notitypes = member.getNotiList();
         *      for noti : noties
         *          noti.run();
         */

    }
}
