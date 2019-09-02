package com.nexters.rezoom.notification.domain;

import com.nexters.rezoom.member.domain.Member;

import javax.persistence.*;

/**
 * Created by momentjin@gmail.com on 2019-09-05
 * Github : http://github.com/momentjin
 */

@Entity
@Table
public class NotificationSetting {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "notification_type")
    private NotificationType notificationType;

    public NotificationSetting(Member member, NotificationType notificationType) {
        this.member = member;
        this.notificationType = notificationType;
    }

    public void notifyToClient(Member receiver, NotificationMessage message) {
        notificationType.notifyToClient(receiver, message);
    }
}
