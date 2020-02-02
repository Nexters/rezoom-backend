package com.nexters.rezoom.core.domain.notification.application;

import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.domain.notification.domain.NotificationMessage;

/**
 * Created by momentjin@gmail.com on 2019-09-06
 * Github : http://github.com/momentjin
 */
public interface Notifier {
    void notifyToClient(Member member, NotificationMessage message);
}
