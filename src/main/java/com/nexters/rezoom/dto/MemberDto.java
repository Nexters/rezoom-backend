package com.nexters.rezoom.dto;

import com.nexters.rezoom.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MemberDto {
    // 회원가입
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpReq {
        @Email
        private String id;

        @NotNull
        @NotEmpty
        private String password;

        @NotNull
        @NotEmpty
        private String name;

        // for test
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
        @Email
        private String id;

        @NotNull
        @NotEmpty
        private String password;

        public SignInReq(String id, String password) {
            this.id = id;
            this.password = password;
        }
    }

    // 회원정보 수정
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateReq {
        @NotNull
        @NotEmpty
        private String name;

        @NotNull
        @NotEmpty
        private String motto;

        @NotNull
        @NotEmpty
        private String profileImageUrl;
    }


    @Getter
    public static class ViewRes {
        private String id;
        private String name;
        private String motto;
        private String profileImageUrl;

        public ViewRes(Member member) {
            this.id = member.getId();
            this.name = member.getName();
            this.motto = member.getMotto();
            this.profileImageUrl = member.getProfileImageUrl();
        }
    }
}
