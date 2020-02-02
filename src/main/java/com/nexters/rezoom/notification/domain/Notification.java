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
@Getter
@Entity
@Table(name = "notification")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "coverletter_id")
    private Long coverletterId;

    @Column(name = "remaining_days")
    private Long remainingDays;

    @Column(name = "remaining_hours")
    private Long remainingHours;

    @Column(name = "is_checked")
    @Builder.Default
    private boolean isChecked = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void toggleChecked() {
        this.isChecked = true;
    }

    @Override
    public String toString() {
        return "[" + companyName + "] 자기소개서의 마감일이 " + remainingDays + "일(" + remainingHours + "시간) 남았습니다.";
    }

}
