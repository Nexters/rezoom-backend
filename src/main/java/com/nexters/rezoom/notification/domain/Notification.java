package com.nexters.rezoom.notification.domain;

import com.nexters.rezoom.member.domain.Member;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by momentjin@gmail.com on 2019-09-02
 * Github : http://github.com/momentjin
 */
@Entity
@Table(name = "notification")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "coverletter_id")
    private long coverletterId;

    @Column(name = "remaining_days")
    private long remainingDays;

    @Column(name = "remaining_hours")
    private long remainingHours;

    @Column(name = "is_checked")
    @Builder.Default
    private boolean isChecked = false;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    public void toggleChecked() {
        this.isChecked = true;
    }

    @Override
    public String toString() {
        return id + "번 자기소개서의 마감일이 " + remainingDays + "일 " + remainingHours + "시간 남았습니다.";
    }

}
