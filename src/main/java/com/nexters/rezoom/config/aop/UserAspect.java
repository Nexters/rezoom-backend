package com.nexters.rezoom.config.aop;

import com.nexters.rezoom.domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.nexters.rezoom.config.security.SecurityConstants.SESSION_USER;
import static com.nexters.rezoom.config.security.SecurityConstants.HEADER_USER_ID;
/**
 * Created by JaeeonJin on 2018-07-23.
 */
@Aspect
@Component
public class UserAspect {

    @Before("execution(* com.nexters.rezoom.controller.*.*(..))")
    public void onBeforeHandler(JoinPoint joinPoint) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();

        HttpSession session = request.getSession();
        User sessionUser = (User) (session.getAttribute(SESSION_USER));

        // 세션이 존재하는 경우에 한해서, header id를 검증한다.
        // 세션이 존재하지 않는 경우 접근될 수 없는 자원은 Security에서 먼저 걸러낸다.
        if (sessionUser != null) {
            int sessionUserId = sessionUser.getUserId();
            String headerUserIdStr = request.getHeader(HEADER_USER_ID);

            // 현재 세션이 있는데도 불구하고 API를 요청한 클라이언트 헤더의 user데이터가 존재하지 않으면 잘못된 요청임
            if (headerUserIdStr==null) {
                sendError(session, response, HttpStatus.BAD_REQUEST, "User info in header not found");
                return;
            }

            // 현재 세션 ID와 클라이언트 헤더 ID를 비교한다.
            int headerUserId = Integer.parseInt(headerUserIdStr);
            if (sessionUserId != headerUserId) {
                sendError(session, response, HttpStatus.FORBIDDEN, "permission error");
                return;
            }
        }
    }

    private static void sendError(HttpSession session, HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.sendError(status.value(), message);
        session.invalidate();
    }

}
