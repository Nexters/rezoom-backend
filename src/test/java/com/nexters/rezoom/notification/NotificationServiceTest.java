package com.nexters.rezoom.notification;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.dto.NotificationDto;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.notification.application.NotificationService;
import com.nexters.rezoom.notification.domain.Notification;
import com.nexters.rezoom.notification.domain.NotificationRepository;
import com.nexters.rezoom.util.TestObjectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

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
        NotificationDto.ListRes notifications = notificationService.createNotificationDatas(member);

        // then
        assertEquals(notifications.getNotifications().size(), 2);
    }

    @Test
    @DisplayName("마감일 알림을 읽었으면 isChecked는 True이어야 한다")
    public void notificationTest2() {
        // given
        Member member = new Member("test", "", "");

        Coverletter coverletter1 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        coverletterRepository.save(coverletter1);

        NotificationDto.ListRes notifications = notificationService.createNotificationDatas(member);
        long notificationId = notifications.getNotifications().get(0).getId();

        // when
        notificationService.toggleCheck(member, notificationId);

        // then
        Notification notification = notificationRepository.findById(member, notificationId);
        assertTrue(notification.isChecked());
    }

}
