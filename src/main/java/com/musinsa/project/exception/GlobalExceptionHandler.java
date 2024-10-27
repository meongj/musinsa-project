package com.musinsa.project.exception;

import com.musinsa.project.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
        BusinessException e
    ) {
        return ResponseEntity.badRequest()
            .body(ErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
        IllegalArgumentException e
    ) {
        return ResponseEntity.badRequest()
            .body(
                new ErrorResponse(
                    ErrorCode.INVALID_PARAMETER.getCode(),
                    e.getMessage()
                )
            );
    }
}
