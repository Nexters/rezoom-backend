package com.nexters.config.exception;

/**
 * Created by momentjin@gmail.com on 2019-04-19
 * Github : http://github.com/momentjin
 **/
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
