package com.musinsa.project.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BusinessExceptionTest {

    @Test
    void shouldHaveErrorCodeAndMessage() {
        // when
        BusinessException exception = new BusinessException(
            ErrorCode.BRAND_NOT_FOUND
        );

        // then
        assertThat(exception).hasMessage(
            ErrorCode.BRAND_NOT_FOUND.getMessage()
        );
        assertThat(exception.getErrorCode()).isEqualTo(
            ErrorCode.BRAND_NOT_FOUND
        );
    }

    @Test
    void errorCodeShouldHaveCodeAndMessage() {
        // when
        ErrorCode errorCode = ErrorCode.BRAND_NOT_FOUND;

        // then
        assertThat(errorCode.getCode()).isEqualTo("B001");
        assertThat(errorCode.getMessage()).isEqualTo(
            "브랜드를 찾을 수 없습니다"
        );
    }
}
