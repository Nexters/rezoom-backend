package com.nexters.rezoom.config.exception;

import com.nexters.rezoom.config.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by momentjin@gmail.com on 2019-04-14
 * Github : http://github.com/momentjin
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final BindingResult bindingResult = e.getBindingResult();
        final List<FieldError> errors = bindingResult.getFieldErrors();

        return ErrorResponse.builder()
                .message("잘못된 요청입니다.")
                .errors(
                        errors.stream().map(error ->
                                ErrorResponse.FieldError.builder()
                                        .reason(error.getDefaultMessage())
                                        .field(error.getField())
                                        .value(String.valueOf(error.getRejectedValue()))
                                        .build()
                        ).collect(Collectors.toList())
                ).build();
    }
}
