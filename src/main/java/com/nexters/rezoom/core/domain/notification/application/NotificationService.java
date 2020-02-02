package com.nexters.rezoom.core.domain.notification.application;

import com.nexters.rezoom.core.domain.coverletter.domain.Coverletter;
import com.nexters.rezoom.core.domain.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.core.domain.coverletter.domain.Deadline;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.domain.member.domain.MemberRepository;
import com.nexters.rezoom.core.domain.notification.api.dto.NotificationDto;
import com.nexters.rezoom.core.domain.notification.domain.Notification;
import com.nexters.rezoom.core.domain.notification.domain.NotificationMessage;
import com.nexters.rezoom.core.domain.notification.domain.NotificationRepository;
import com.nexters.rezoom.core.domain.notification.domain.NotificationSetting;
import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
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
        List<Notification> notifications = notificationRepository.findAllByMemberOrderByCreatedAtDesc(member);

        return new NotificationDto.ListRes(notifications);
    }

    @Transactional
    public Notification toggleCheck(Member member, Long notificationId) {
        Optional<Notification> notification = notificationRepository.findByIdAndMember(notificationId, member);
        notification.ifPresent(Notification::toggleChecked);

        return notification.orElseThrow(() -> new BusinessException(ErrorType.NOTIFICATION_NOT_FOUND));
    }

    public void createNotifications() {

        // TODO : 휴먼 회원 제외하기?
        // 모든 유저의 자기소개서를 가져온다. (단, 마감일이 있고 지원하지 않은 자기소개서만)
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
        // TODO : 현재 활성화된 유저만 조회할 수 있도록 수정해야 한다. (필드가 필요함, 로그인 접속 기록 등으로 추가해서 확인할 것)
        List<Member> receivers = memberRepository.findAll();

        // notify 시작
        for (Member member : receivers) {

            // 해당 유저의 알림 데이터가 있는지 확인
            List<Notification> notifications = notificationRepository.findAllByMemberOrderByCreatedAtDesc(member);

            // 유저의 알림 설정 조회
            Set<NotificationSetting> notificationSettings = null; // member.getNotificationSettings();

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
