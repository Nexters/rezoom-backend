package com.nexters.rezoom.member.dto;

import com.nexters.rezoom.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class MemberDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpReq {

        @Email
        private String id;

        @NotEmpty
        private String password;

        @NotEmpty
        private String name;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignInReq {
        @Email
        private String id;

        @NotEmpty
        private String password;

        public SignInReq(String id, String password) {
            this.id = id;
            this.password = password;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateReq {
        @NotEmpty
        private String name;

        private String motto;

        private String profileImageUrl;
    }

    @Getter
    public static class ViewRes {
        private String id;
        private String name;
        private String motto;

        public ViewRes(Member member) {
            this.id = member.getId();
            this.name = member.getName();
            this.motto = member.getMotto();
        }
    }
}
