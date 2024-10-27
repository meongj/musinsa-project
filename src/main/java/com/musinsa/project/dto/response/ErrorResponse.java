package com.musinsa.project.dto.response;

import com.musinsa.project.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }
}
