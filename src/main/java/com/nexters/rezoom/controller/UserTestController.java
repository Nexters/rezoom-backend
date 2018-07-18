package com.nexters.rezoom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by JaeeonJin on 2018-07-18.
 */
@RestController
public class UserTestController {

    /**
     * 현 세션에 인증된 사용자의 username 얻는 예제
     * @param principal 현재 세션에서 인증된 사용자 객체
     * @return
     */

    @GetMapping
    public String getUsernameTest(Principal principal) {
        return principal.getName();
    }

}
