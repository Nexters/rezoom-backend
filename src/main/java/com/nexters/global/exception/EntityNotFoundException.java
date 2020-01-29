package com.nexters.global.exception;

/**
 * Created by momentjin@gmail.com on 2019-04-19
 * Github : http://github.com/momentjin
 **/
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorType errorType) {
        super(errorType);
    }
}
