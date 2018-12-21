package com.nexters.rezoom.config.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.nexters.rezoom.config.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        Member member;
        try {
            member = new ObjectMapper().readValue(req.getInputStream(), Member.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("member 파라미터 불일치");
        }

        return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(member.getId(), member.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        String token = JWT.create()
                .withClaim("id", ((CustomUserDetail) auth.getPrincipal()).getUsername())
                .withClaim("name", ((CustomUserDetail) auth.getPrincipal()).getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.getWriter().append(TOKEN_PREFIX).append(token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "로그인 실패");
    }
}