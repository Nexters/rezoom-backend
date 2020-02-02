package com.nexters.rezoom.core.global.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by momentjin@gmail.com on 2020-02-02
 * Github : http://github.com/momentjin
 */

@Getter
public class FieldErrorWrapper {

    private List<FieldErrorWrapper.FieldError> errors;

    private FieldErrorWrapper(List<FieldError> errors) {
        this.errors = errors;
    }

    public static FieldErrorWrapper of(List<org.springframework.validation.FieldError> fieldErrors) {
        List<FieldErrorWrapper.FieldError> errors = fieldErrors.stream()
                .map(error -> FieldErrorWrapper.FieldError
                        .builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value(String.valueOf(error.getRejectedValue()))
                        .build())
                .collect(Collectors.toList());

        return new FieldErrorWrapper(errors);
    }


    @Getter
    private static class FieldError {

        private String field;
        private String value;
        private String reason;

        @Builder
        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}
