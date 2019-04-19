package com.nexters.rezoom.config.dto;

import com.nexters.rezoom.config.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by momentjin@gmail.com on 2019-04-14
 * Github : http://github.com/momentjin
 **/
@Getter
public class ErrorResponse {
    private String message;
    private int status;
    private List<FieldError> errors = new ArrayList<>();

    public ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
    }

    public ErrorResponse(ErrorCode errorCode, List<org.springframework.validation.FieldError> fieldErrors) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.errors = fieldErrors.stream()
                .map(error -> ErrorResponse.FieldError
                        .builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value(String.valueOf(error.getRejectedValue()))
                        .build())
                .collect(Collectors.toList());
    }

    @Builder
    @Getter
    private static class FieldError {
        private String field;
        private String value;
        private String reason;
    }
}