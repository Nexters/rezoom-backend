package com.nexters.rezoom.notification;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import com.nexters.rezoom.notification.application.NotificationService;
import com.nexters.rezoom.notification.domain.Notification;
import com.nexters.rezoom.notification.domain.NotificationRepository;
import com.nexters.rezoom.notification.domain.NotificationSetting;
import com.nexters.rezoom.notification.domain.NotificationType;
import com.nexters.rezoom.notification.dto.NotificationDto;
import com.nexters.rezoom.util.TestObjectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by momentjin@gmail.com on 2019-09-02
 * Github : http://github.com/momentjin
 */

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CoverletterRepository coverletterRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("마감일 알림 데이터 생성 테스트")
    public void notificationTest1() {
        // given
        Member member = new Member("test", "", "");
        Coverletter coverletter1 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        Coverletter coverletter2 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);

        coverletterRepository.save(coverletter1);
        coverletterRepository.save(coverletter2);

        // when
        notificationService.createNotifications();

        // then
        NotificationDto.ListRes notifications = notificationService.getNotifications(member);
        assertEquals(notifications.getNotifications().size(), 2);
    }

    @Test
    @DisplayName("마감일 알림을 읽었으면 isChecked는 True이어야 한다")
    public void notificationTest2() {
        // given
        Member member = new Member("test", "", "");

        Coverletter coverletter1 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        coverletterRepository.save(coverletter1);

        notificationService.createNotifications();
        NotificationDto.ListRes notifications = notificationService.getNotifications(member);
        long notificationId = notifications.getNotifications().get(0).getId();

        // when
        notificationService.toggleCheck(member, notificationId);

        // then
        Optional<Notification> notification = notificationRepository.findByIdAndMember(notificationId, member);
        assertTrue(notification.get().isChecked());
    }


//    @Test
//    @DisplayName("메일 알림 테스트")
    public void notificationTest3() {
        // given
        Member member = new Member("wlswodjs_@naver.com", "", "");

        NotificationSetting setting1 = new NotificationSetting(member, NotificationType.EMAIL);
        member.addNotificationSetting(setting1);

        memberRepository.save(member);

        Coverletter coverletter1 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        coverletterRepository.save(coverletter1);

        Coverletter coverletter2 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        coverletterRepository.save(coverletter2);

        notificationService.createNotifications();

        // when
        notificationService.sendNotifications();

        // then
        // 성공 결과는 수신자의 이메일을 확인하라..
    }
}
