package com.nexters.config.security;

/**
 * Created by JaeeonJin on 2018-07-31.
 */
public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "JWT ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/members/signup";
}
