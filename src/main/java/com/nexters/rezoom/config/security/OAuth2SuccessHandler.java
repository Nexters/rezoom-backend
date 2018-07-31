package com.nexters.rezoom.config.security;

import com.nexters.rezoom.domain.User;
import com.nexters.rezoom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.nexters.rezoom.config.security.SecurityConstants.SESSION_USER;
import static com.nexters.rezoom.config.security.SecurityConstants.HEADER_USER_ID;

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

	/**
	 * 소셜 로그인 성공시 실행되는 메소드
	 * - 인증된 사용자의 username으로 DB를 조회하고 없으면 저장한다.
	 * - 세션에 userId를 저장함
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {
        String userName = auth.getName();

        User user = userRepository.selectOne(userName, socialType);
        if (user == null) {
        	user = new User(userName, socialType);
			userRepository.insertOne(user);
		}

		HttpSession session = req.getSession();
		if (session != null) {
			session.setAttribute(SESSION_USER, user);
			res.setHeader(HEADER_USER_ID, user.getUserId()+"");
		}
	}
}
