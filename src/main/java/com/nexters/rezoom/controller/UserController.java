package com.nexters.rezoom.controller;

import com.nexters.rezoom.domain.User;
import com.nexters.rezoom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nexters.rezoom.config.security.SecurityConstants.SESSION_USER;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by JaeeonJin on 2018-07-21.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    // 현재 세션에 저장된 유저 데이터를 얻는다.
    @GetMapping
    public User getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USER);
        return user;
    }

    // TODO : 소셜 인증 해제 API 구현
    // 찾아보니까 딱히 없는 것 같음. 실제 페이지로 리디렉션시켜줘야 할듯..

}
