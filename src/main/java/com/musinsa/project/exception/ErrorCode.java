package com.musinsa.project.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 브랜드 관련 에러
    BRAND_NOT_FOUND("B001", "브랜드를 찾을 수 없습니다"),
    INVALID_CATEGORY_PRODUCTS("B002", "모든 카테고리의 상품이 필요합니다"),

    // 상품 관련 에러
    PRODUCT_NOT_FOUND("P001", "상품을 찾을 수 없습니다"),
    INVALID_PRODUCT_PRICE("P002", "상품 가격이 유효하지 않습니다");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
