package com.nexters.rezoom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by JaeeonJin on 2018-08-22.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "이미 존재하는 이메일입니다.")
public class DuplicateEmailException extends RuntimeException {
}
