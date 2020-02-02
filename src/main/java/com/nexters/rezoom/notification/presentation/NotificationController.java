package com.nexters.rezoom.notification.presentation;

import com.nexters.global.dto.ApiResponse;
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
    public ApiResponse<NotificationDto.ListRes> getNotifications(@AuthenticationPrincipal Member member) {
        return ApiResponse.success(notificationService.getNotifications(member));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse toggleCheck(@AuthenticationPrincipal Member member, @PathVariable(name = "id") long notificationId) {
        return ApiResponse.success(notificationService.toggleCheck(member, notificationId));
    }

}
