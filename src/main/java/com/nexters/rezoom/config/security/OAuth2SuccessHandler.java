package com.nexters.rezoom.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nexters.rezoom.domain.User;
import com.nexters.rezoom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 소셜 로그인 성공시 실행됌
 * @author JaeeonJin
 *
 */
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	UserRepository userRepository;

	final String socialType;

	public OAuth2SuccessHandler(String socialType, UserRepository userRepository) {
	    this.socialType = socialType;
	    this.userRepository = userRepository;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {
        String userName = auth.getName();

        User user = userRepository.selectOne(userName, socialType);
        if (user == null)  userRepository.insertOne(new User(userName, socialType));

        // TODO : 홈으로 가는지 테스트
        res.sendRedirect("/");
	}

}
