package com.nexters.rezoom.core.domain.member.api;

import com.nexters.rezoom.core.global.dto.ApiResponse;
import com.nexters.rezoom.core.domain.member.application.RezoomMemberService;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.domain.member.api.dto.MemberDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final RezoomMemberService service;

    public MemberController(RezoomMemberService service) {
        this.service = service;
    }

    // login api는 spring security에 의해 자동생성됌 (path: /login)

    @GetMapping(value = "/me")
    public ApiResponse<MemberDto.ViewRes> getCurrentUserInfo(@AuthenticationPrincipal Member member) {
        return ApiResponse.success(service.getMemberInfo((member.getId())));
    }

    @PostMapping(value = "/signup")
    public ApiResponse signUp(@Valid @RequestBody MemberDto.SignUpReq req) {
        service.signUp(req);
        return ApiResponse.success();
    }

    @PutMapping
    public ApiResponse updateMemberInfo(@AuthenticationPrincipal Member member, @Valid @RequestBody MemberDto.UpdateReq req) {
        service.updateMemberInfo(member.getId(), req);
        return ApiResponse.success();
    }

}
