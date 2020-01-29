package com.nexters.rezoom.notification.application;

import com.nexters.global.exception.EntityNotFoundException;
import com.nexters.global.exception.ErrorType;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.coverletter.domain.Deadline;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import com.nexters.rezoom.notification.domain.Notification;
import com.nexters.rezoom.notification.domain.NotificationMessage;
import com.nexters.rezoom.notification.domain.NotificationRepository;
import com.nexters.rezoom.notification.domain.NotificationSetting;
import com.nexters.rezoom.notification.dto.NotificationDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by momentjin@gmail.com on 2019-09-02
 * Github : http://github.com/momentjin
 */

@Service
public class NotificationService {

    private final MemberRepository memberRepository;
    private final CoverletterRepository coverletterRepository;
    private final NotificationRepository notificationRepository;

    public NotificationService(MemberRepository memberRepository, CoverletterRepository coverletterRepository,
                               NotificationRepository notificationRepository) {

        this.memberRepository = memberRepository;
        this.coverletterRepository = coverletterRepository;
        this.notificationRepository = notificationRepository;
    }

    public NotificationDto.ListRes getNotifications(Member member) {
        List<Notification> notifications = notificationRepository.findAllByMember(member);
        notifications.sort((c1, c2) -> c2.getCreateDate().compareTo(c1.getCreateDate())); // 내림차순 정렬

        return new NotificationDto.ListRes(notifications);
    }

    @Transactional
    public Notification toggleCheck(Member member, long notificationId) {
        Optional<Notification> notification = notificationRepository.findByIdAndMember(notificationId, member);
        notification.ifPresent(Notification::toggleChecked);

        return notification.orElseThrow(() -> new EntityNotFoundException(ErrorType.NOTIFICATION_NOT_FOUND));
    }

    public void createNotifications() {

        // 모든 유저의 자기소개서를 가져온다. (단, 마감일이 있고 지원하지 않은 자기소개서만)
        // TODO : 휴먼 회원 제외하기?
        List<Coverletter> coverletters = coverletterRepository.findAllByDeadlineGreaterThanEqual(Deadline.now());

        // 가져온 자소서 리스트를 순회하며 알림 데이터를 notification table에 저장한다.
        for (Coverletter coverletter : coverletters) {
            Notification notification = Notification.builder()
                    .member(coverletter.getMember())
                    .companyName(coverletter.getCompanyName())
                    .coverletterId(coverletter.getId())
                    .remainingDays(coverletter.getDeadline().getRemainingDays())
                    .remainingHours(coverletter.getDeadline().getRemainingHours())
                    .build();

            notificationRepository.save(notification);
        }
    }

    public void sendNotifications() {

        // 알림을 받을 active user 조회
        List<Member> receivers = memberRepository.findAll();

        // notify 시작
        for (Member member : receivers) {

            // 해당 유저의 알림 데이터가 있는지 확인
            List<Notification> notifications = notificationRepository.findAllByMember(member);

            // 유저의 알림 설정 조회
            Set<NotificationSetting> notificationSettings = member.getNotificationSettings();

            for (Notification notification : notifications) {
                NotificationMessage message = new NotificationMessage(
                        "자기소개서 마감일 알림",
                        notification.toString()
                );

                for (NotificationSetting setting : notificationSettings) {
                    setting.notifyToClient(member, message);
                }
            }
        }
    }

}
