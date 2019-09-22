package com.nexters.rezoom.notification.domain;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.notification.application.EmailNotificator;
import com.nexters.rezoom.notification.application.NoneNotificator;
import com.nexters.rezoom.notification.application.Notificator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by momentjin@gmail.com on 2019-09-05
 * Github : http://github.com/momentjin
 */
public enum NotificationType {

    EMAIL(0, new EmailNotificator()),
    NONE(1, new NoneNotificator());

    private static final Map<Integer, NotificationType> lookup = new HashMap<>();

    static {
        for (NotificationType notificationType : NotificationType.values()) {
            lookup.put(notificationType.typeNo, notificationType);
        }
    }

    private int typeNo;
    private Notificator notificator;

    NotificationType(int typeNo, Notificator notificator) {
        this.typeNo = typeNo;
        this.notificator = notificator;
    }

    public static NotificationType getValue(int typeNo) {
        return lookup.getOrDefault(typeNo, NotificationType.NONE);
    }

    public void notifyToClient(Member member, NotificationMessage message) {
        notificator.notifyToClient(member, message);
    }

}
