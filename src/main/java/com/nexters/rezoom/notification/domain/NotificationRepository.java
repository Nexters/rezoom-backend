package com.nexters.rezoom.notification.domain;

import com.nexters.rezoom.member.domain.Member;

import java.util.List;

/**
 * Created by momentjin@gmail.com on 2019-09-02
 * Github : http://github.com/momentjin
 */
public interface NotificationRepository {
    void save(Notification notification);

    List<Notification> selectAll(Member member);
}
