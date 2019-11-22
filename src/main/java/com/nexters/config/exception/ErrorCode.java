package com.nexters.config.exception;

import lombok.Getter;

/**
 * Created by momentjin@gmail.com on 2019-04-19
 * Github : http://github.com/momentjin
 **/
@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "잘못된 입력값입니다."),

    EMAIL_DUPLICATION(400, "이미 등록된 이메일입니다."),
    WRONG_PASSWORD(400, "비밀번호가 틀립니다."),

    ENTITY_NOT_FOUND(404, ""),
    MEMBER_NOT_FOUND(404, "존재하지 않는 계정입니다."),
    COVERLETTER_NOT_FOUND(404, "존재하지 않는 자기소개서입니다."),
    QUESTION_NOT_FOUND(404, "존재하지 않는 문항입니다."),
    HASHTAG_NOT_FOUND(404, "존재하지 않는 태그입니다."),

    INVALID_ARGUMENT(400);

    private int status;
    private String message;

    ErrorCode(int status) {
        this.status = status;
    }

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }


}
