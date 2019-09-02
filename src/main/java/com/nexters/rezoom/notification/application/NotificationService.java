package com.nexters.rezoom.notification.application;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.notification.domain.Notification;
import com.nexters.rezoom.notification.domain.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by momentjin@gmail.com on 2019-09-02
 * Github : http://github.com/momentjin
 */

@Service
public class NotificationService {

    private final CoverletterRepository coverletterRepository;
    private final NotificationRepository notificationRepository;

    public NotificationService(CoverletterRepository coverletterRepository, NotificationRepository notificationRepository) {
        this.coverletterRepository = coverletterRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> createNotificationDatas(Member member) {

        // 자기소개서를 가져온다. (단, 마감일이 있고 지원하지 않은 자기소개서만)
        List<Coverletter> coverletters = coverletterRepository.findByDeadline(member);

        // 가져온 자소서 리스트를 순회하며 알림 데이터를 notification table에 저장한다.
        for (Coverletter coverletter : coverletters) {

            Notification notification = Notification.builder()
                    .member(member)
                    .coverletterId(coverletter.getId())
                    .remainingDays(coverletter.getDeadline().getRemainingDays())
                    .remainingHours(coverletter.getDeadline().getRemainingHours())
                    .build();

            notificationRepository.save(notification);
        }

        return notificationRepository.selectAll(member);
    }

    @Transactional
    public void toggleCheck(Member member, Notification notification) {
        notification.setMember(member);
        notification.toggleChecked();
    }

}
