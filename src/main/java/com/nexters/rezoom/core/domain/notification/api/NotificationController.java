package com.nexters.rezoom.core.domain.notification.api;

import com.nexters.rezoom.core.global.dto.ApiResponse;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.domain.notification.application.NotificationService;
import com.nexters.rezoom.core.domain.notification.api.dto.NotificationDto;
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
    public ApiResponse<NotificationDto.ListRes> getNotifications(@AuthenticationPrincipal Member member) {
        return ApiResponse.success(notificationService.getNotifications(member));
    }

    @PutMapping("/{id}")
    public ApiResponse toggleCheck(@AuthenticationPrincipal Member member, @PathVariable(name = "id") Long notificationId) {
        return ApiResponse.success(notificationService.toggleCheck(member, notificationId));
    }

}
