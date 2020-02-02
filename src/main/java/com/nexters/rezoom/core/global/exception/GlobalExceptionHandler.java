package com.nexters.rezoom.core.global.exception;

import com.nexters.rezoom.core.global.dto.ApiResponse;
import com.nexters.rezoom.core.global.dto.FieldErrorWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Created by momentjin@gmail.com on 2019-04-14
 * Github : http://github.com/momentjin
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final BindingResult bindingResult = e.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final FieldErrorWrapper fieldErrorWrapper = FieldErrorWrapper.of(fieldErrors);
        final ApiResponse response = ApiResponse.fail(fieldErrorWrapper, ErrorType.INVALID_INPUT_VALUE);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse> handleBusinessException(BusinessException e) {
        ApiResponse response = ApiResponse.fail(e.getErrorType());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse> handleException(Exception e) {
        ApiResponse response = ApiResponse.error(ErrorType.SYSTEM_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
