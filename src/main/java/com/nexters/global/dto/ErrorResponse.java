package com.nexters.global.dto;

import com.nexters.global.exception.ErrorType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by momentjin@gmail.com on 2019-04-14
 * Github : http://github.com/momentjin
 **/
@Getter
public class ErrorResponse {

    @Setter
    private String message;
    private int status;
    private List<FieldError> errors = new ArrayList<>();

    public ErrorResponse(ErrorType errorType) {
        this.message = errorType.getMessage();
    }

    public ErrorResponse(ErrorType errorType, List<org.springframework.validation.FieldError> fieldErrors) {
        this.message = errorType.getMessage();
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