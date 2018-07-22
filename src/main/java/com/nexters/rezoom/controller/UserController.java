package com.nexters.rezoom.controller;

import com.nexters.rezoom.domain.User;
import com.nexters.rezoom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // GET : /
    // 한명의 유저 데이터를 얻는다.
    // 메인 페이지에서 회원 이름, 소셜 타입 보여줄 때 쓰임.
    @GetMapping("/current")
    public User getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USER);
        return user;
    }

}
