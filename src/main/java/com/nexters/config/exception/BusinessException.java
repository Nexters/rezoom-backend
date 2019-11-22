package com.nexters.config.exception;

import lombok.Getter;

/**
 * Created by momentjin@gmail.com on 2019-04-19
 * Github : http://github.com/momentjin
 **/
class BusinessException extends RuntimeException {

    @Getter
    private ErrorCode errorCode;

    BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
