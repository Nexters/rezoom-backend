package com.nexters.rezoom.notification.application;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.notification.domain.NotificationMessage;

/**
 * Created by momentjin@gmail.com on 2019-09-06
 * Github : http://github.com/momentjin
 */
public interface Notificator {
    void notifyToClient(Member member, NotificationMessage message);
}
