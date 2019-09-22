package com.nexters.rezoom.notification.presentation;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.notification.application.NotificationService;
import com.nexters.rezoom.notification.dto.NotificationDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Created by momentjin@gmail.com on 2019-09-03
 * Github : http://github.com/momentjin
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public NotificationDto.ListRes getNotifications(@AuthenticationPrincipal Member member) {
        return notificationService.getNotifications(member);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleCheck(@AuthenticationPrincipal Member member, @PathVariable(name = "id") long notificationId) {
        notificationService.toggleCheck(member, notificationId);
    }

}
