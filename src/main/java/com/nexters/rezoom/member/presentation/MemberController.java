package com.nexters.rezoom.member.presentation;

import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.member.application.MemberService;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService service;

    // login api는 security에 의해 자동생성됌 (/login)

    @GetMapping(value = "/me")
    @ResponseStatus(HttpStatus.OK)
    public MemberDto.ViewRes getCurrentUserInfo(@AuthenticationPrincipal Member member) {
        return new MemberDto.ViewRes(member);
    }

    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody MemberDto.SignUpReq req) {
        service.signUp(req);
    }

}
