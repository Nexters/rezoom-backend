package com.nexters.rezoom.core.global.dto;

import com.nexters.rezoom.core.global.exception.ErrorType;
import lombok.Builder;

/**
 * Created by momentjin@gmail.com on 2020-02-02
 * Github : http://github.com/momentjin
 */

public class ApiResponse<T> {

    private ResponseType responseType;
    private String message;
    private T data;

    @Builder
    private ApiResponse(String message, T data, ResponseType responseType) {
        this.message = message;
        this.data = data;
        this.responseType = responseType;

    }

    public static ApiResponse success() {
        return ApiResponse.builder().build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponseBuilder<T>()
                .data(data)
                .build();
    }

    public static <T> ApiResponse fail(ErrorType errorType) {
        return new ApiResponseBuilder<T>()
                .message(errorType.getMessage())
                .build();
    }

    public static <T> ApiResponse<T> fail(T data, ErrorType errorType) {
        return new ApiResponseBuilder<T>()
                .message(errorType.getMessage())
                .data(data)
                .build();
    }

    public static <T> ApiResponse error(ErrorType errorType) {
        return new ApiResponseBuilder<T>()
                .message(errorType.getMessage())
                .build();
    }

    public static <T> ApiResponse<T> error(T data, ErrorType errorType) {
        return new ApiResponseBuilder<T>()
                .message(errorType.getMessage())
                .data(data)
                .build();
    }
}
