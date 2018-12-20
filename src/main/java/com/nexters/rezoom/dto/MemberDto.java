package com.nexters.rezoom.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {
    // 회원가입
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpReq {
        private String id;
        private String password;
        private String name;

        public SignUpReq(String id, String password, String name) {
            this.id = id;
            this.password = password;
            this.name = name;
        }
    }

    // 로그인
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignInReq {
        private String id;
        private String password;

        public SignInReq(String id, String password) {
            this.id = id;
            this.password = password;
        }
    }

    @Getter
    public static class ViewRes {
        private String id;
        private String name;

        public ViewRes(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
