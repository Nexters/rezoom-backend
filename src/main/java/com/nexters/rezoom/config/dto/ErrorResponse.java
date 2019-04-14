package com.nexters.rezoom.config.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Created by momentjin@gmail.com on 2019-04-14
 * Github : http://github.com/momentjin
 **/
@Builder
@Getter
public class ErrorResponse {
    private String message;
    private List<FieldError> errors;

    @Builder
    @Getter
    public static class FieldError {
        private String field;
        private String value;
        private String reason;
    }
}