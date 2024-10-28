package com.musinsa.project.dto.response;

import com.musinsa.project.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "에러 응답 DTO")
@Getter
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "BRAND_NOT_FOUND")
    private final String code;

    @Schema(description = "에러 메시지")
    private final String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }
}
