package com.nexters.rezoom.notification;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.notification.application.NotificationService;
import com.nexters.rezoom.notification.domain.Notification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Test
    @DisplayName("마감일 알림 데이터 생성 테스트")
    public void nofityTest1() {
        // given
        Member member = new Member("s_wlswodjs@naver.com1", "", "");

        // when
        List<Notification> notifications = notificationService.createNotificationDatas(member);

        // then
        notifications.forEach(x -> System.out.println(x.toString()));
    }

}
