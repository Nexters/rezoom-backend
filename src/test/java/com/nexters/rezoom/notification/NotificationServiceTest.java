package com.nexters.rezoom.notification;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.coverletter.domain.Deadline;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import com.nexters.rezoom.member.domain.OAuth2Member;
import com.nexters.rezoom.notification.application.NotificationService;
import com.nexters.rezoom.notification.domain.Notification;
import com.nexters.rezoom.notification.domain.NotificationRepository;
import com.nexters.rezoom.notification.domain.NotificationSetting;
import com.nexters.rezoom.notification.domain.NotificationType;
import com.nexters.rezoom.notification.dto.NotificationDto;
import com.nexters.util.TestObjectUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Created by momentjin@gmail.com on 2019-09-02
 * Github : http://github.com/momentjin
 */

@SpringBootTest(classes = {NotificationService.class})
@ExtendWith(SpringExtension.class)
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    private static Member member;

    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private CoverletterRepository coverletterRepository;

    @MockBean
    private MemberRepository memberRepository;

    @BeforeAll
    public static void setup() {
        member = TestObjectUtils.createTestMember();
    }

    /**
     * 테스트일시 기준, 자기소개서 마감일이 지나지 않았다면 Notification 데이터가 생성되어야 한다.
     */
    @Test
    public void notification_생성_성공() {
        // given
        LocalDateTime deadlineDateTime = LocalDateTime.now().plusDays(5);
        Deadline baseDeadline = new Deadline(deadlineDateTime);
        Coverletter coverletter = Coverletter.builder()
                .id(10L)
                .companyName("companyName")
                .member(member)
                .deadline(baseDeadline)
                .build();

        given(coverletterRepository.findAllByDeadlineGreaterThanEqual(any(Deadline.class)))
                .willReturn(Collections.singletonList(coverletter));
//
//        given(notificationRepository.findAllByMember(member))
//                .willReturn(Collections.singletonList(Notification.builder()
//                        .build()));

        // when
        notificationService.createNotifications();

        // then
        verify(notificationRepository, atLeastOnce()).save(any(Notification.class));

        NotificationDto.ListRes notifications = notificationService.getNotifications(member);
        assertEquals(notifications.getNotifications().size(), 1);
    }

    @Test
    public void notification_조회시_isChecked는_TRUE() {
        // given
//        given(notificationRepository.findAllByMember(any(Member.class)))
//                .willReturn(Collections.singletonList(Notification.builder().build()));

        // 알림 조회
        NotificationDto.ListRes notifications = notificationService.getNotifications(member);
        Long notificationId = notifications.getNotifications().get(0).getId();

        // when
        when(notificationRepository.findByIdAndMember(anyLong(), any(Member.class)))
                .thenReturn(Optional.of(Notification.builder().build()));

        Notification resultNotification = notificationService.toggleCheck(member, notificationId);

        // then
        assertTrue(resultNotification.isChecked());
    }

    /**
     * 약 5s동안 실행되므로, 필요시 @Test 설정해서 테스트
     */
    public void 이메일_알림_테스트() {
        // given
        Member member = new Member("s_wlswodjs@naver.com", "tester", "password");
        NotificationSetting setting = new NotificationSetting(member, NotificationType.EMAIL);
//        member.addNotificationSetting(setting);

        given(memberRepository.findAll())
                .willReturn(Collections.singletonList(member));

//        given(notificationRepository.findAllByMember(member)).willReturn(
//                Collections.singletonList(
//                        Notification.builder()
//                                .id(12L)
//                                .companyName("testCompany")
//                                .remainingHours(24)
//                                .remainingDays(1)
//                                .coverletterId(51L)
//                                .member(member)
//                                .createdAt(LocalDateTime.now())
//                                .build()));

        // when
        notificationService.sendNotifications();

        // then
        // 성공 결과는 수신자의 이메일을 확인...
    }

    /**
     * 테스트 필요시, @Test 설정해서 테스트
     * <p>
     * 본 테스트를 수행하기 위해선 access_token이 필요합니다.
     * 1. 웹브라우저를 통해 다음 URL을 입력해서 access_token 획득하기
     * 2. member table에 해당하는 row를 찾아 access_token 조회
     * 3. [access_token]에 실제 access_token 값 입력
     * <p>
     * access_token 획득 URL = http://localalhost:8080/oauth2/authorization/kakao
     * ex) http://localalhost:8080/oauth2/authorization/kakao
     */
    public void 카카오톡_알림_테스트() {
        // given
        OAuth2Member member = OAuth2Member.OAuth2MemberBuilder()
                .id("test")
                .name("tester")
                .providerType("kakao")
                .accessToken("cDgT_8Vqwi6QR43vCfKxw7cj4FC_7AvGIkiycgo9dRoAAAFvKF32xw")
                .build();

        NotificationSetting setting = new NotificationSetting(member, NotificationType.KAKAO);
//        member.addNotificationSetting(setting);

        given(memberRepository.findAll())
                .willReturn(Collections.singletonList(member));
//
//        given(notificationRepository.findAllByMember(member)).willReturn(
//                Collections.singletonList(
//                        Notification.builder()
//                                .id(12L)
//                                .companyName("testCompany")
//                                .remainingHours(24)
//                                .remainingDays(1)
//                                .coverletterId(51L)
//                                .member(member)
//                                .createdAt(LocalDateTime.now())
//                                .build()));

        // when
        notificationService.sendNotifications();

        // then
        // 성공 결과는 수신자의 카톡을 확인..
    }
}
