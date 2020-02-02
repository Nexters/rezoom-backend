package com.nexters.rezoom.core.domain.notification.domain;

import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.domain.notification.application.EmailNotifier;
import com.nexters.rezoom.core.domain.notification.application.NoneNotifier;
import com.nexters.rezoom.core.domain.notification.application.Notifier;
import com.nexters.rezoom.core.domain.notification.application.KakaoNotifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by momentjin@gmail.com on 2019-09-05
 * Github : http://github.com/momentjin
 */
public enum NotificationType {

    EMAIL(0, new EmailNotifier()),
    KAKAO(1, new KakaoNotifier()),
    NONE(9, new NoneNotifier());

    private static final Map<Integer, NotificationType> lookup = new HashMap<>();

    static {
        for (NotificationType notificationType : NotificationType.values()) {
            lookup.put(notificationType.typeNo, notificationType);
        }
    }

    private int typeNo;
    private Notifier notifier;

    NotificationType(int typeNo, Notifier notifier) {
        this.typeNo = typeNo;
        this.notifier = notifier;
    }

    public static NotificationType getValue(int typeNo) {
        return lookup.getOrDefault(typeNo, NotificationType.NONE);
    }

    public void notifyToClient(Member member, NotificationMessage message) {
        notifier.notifyToClient(member, message);
    }

}
