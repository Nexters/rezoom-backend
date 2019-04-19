package com.nexters.rezoom.config.exception;

/**
 * Created by momentjin@gmail.com on 2019-04-19
 * Github : http://github.com/momentjin
 **/
public class InvalidValueException extends BusinessException {

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }

}
