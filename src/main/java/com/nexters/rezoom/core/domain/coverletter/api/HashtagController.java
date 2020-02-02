package com.nexters.rezoom.core.domain.coverletter.api;

import com.nexters.rezoom.core.domain.coverletter.application.HashtagService;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.global.dto.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hashtags")
public class HashtagController {

    private final HashtagService service;

    public HashtagController(HashtagService service) {
        this.service = service;
    }

    @GetMapping(value = "")
    public ApiResponse<List<String>> getMyHashtags(@AuthenticationPrincipal Member member) {
        return ApiResponse.success(service.getMyHashtags(member));
    }
}
