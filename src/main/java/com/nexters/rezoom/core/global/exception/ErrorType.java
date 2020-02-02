package com.nexters.rezoom.core.global.exception;

import lombok.Getter;

/**
 * Created by momentjin@gmail.com on 2019-04-19
 * Github : http://github.com/momentjin
 **/
@Getter
public enum ErrorType {

    MEMBER_NOT_FOUND("존재하지 않는 계정입니다."),
    COVERLETTER_NOT_FOUND("존재하지 않는 자기소개서입니다."),
    QUESTION_NOT_FOUND("존재하지 않는 문항입니다."),
    HASHTAG_NOT_FOUND("존재하지 않는 태그입니다."),
    NOTIFICATION_NOT_FOUND("존재하지 않는 알림입니다."),
    PROFILE_IMG_NOT_FOUND("프로필 이미지를 찾을 수 없습니다"),

    INVALID_INPUT_VALUE("잘못된 입력값입니다."),
    EMAIL_DUPLICATION("이미 등록된 이메일입니다."),
    WRONG_PASSWORD("비밀번호가 틀립니다."),
    UNSURPPOTED_FILE_EXTENTION("지원하지 않는 파일 형식입니다."),

    SYSTEM_ERROR;


    private String message;

    ErrorType() {
        this.message = "";
    }

    ErrorType(String message) {
        this.message = message;
    }
}
