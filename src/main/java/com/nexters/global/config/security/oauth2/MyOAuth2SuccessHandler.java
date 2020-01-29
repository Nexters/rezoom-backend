package com.nexters.global.config.security.oauth2;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nexters.global.config.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by momentjin@gmail.com on 2019-12-12
 * Github : http://github.com/momentjin
 */

@Component
public class MyOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${client.oauth2-callback}")
    private String clientOAuth2CallBack;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String id = (String) oauth2User.getAttributes().get("id");
        String name = oauth2User.getName();

        String token = JWT.create()
                .withClaim("id", id)
                .withClaim("name", name)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));

        res.sendRedirect(clientOAuth2CallBack + "?token=" + SecurityConstants.TOKEN_PREFIX + token);
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        res.getWriter()
                .append("{\"token\":")
                .append("\"")
                .append(SecurityConstants.TOKEN_PREFIX)
                .append(token)
                .append("\"}")
                .flush();
    }
}
