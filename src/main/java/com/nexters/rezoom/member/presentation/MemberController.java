package com.nexters.rezoom.member.presentation;

import com.nexters.global.dto.ApiResponse;
import com.nexters.rezoom.member.application.MemberService;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    // login api는 spring security에 의해 자동생성됌 (path: /login)

    @GetMapping(value = "/me")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<MemberDto.ViewRes> getCurrentUserInfo(@AuthenticationPrincipal Member member) {
        return ApiResponse.success(service.getMemberInfo((member.getId())));
    }

    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse signUp(@Valid @RequestBody MemberDto.SignUpReq req) {
        service.signUp(req);
        return ApiResponse.success();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse updateMemberInfo(@AuthenticationPrincipal Member member, @Valid @RequestBody MemberDto.UpdateReq req) {
        service.updateMemberInfo(member.getId(), req);
        return ApiResponse.success();
    }

}
