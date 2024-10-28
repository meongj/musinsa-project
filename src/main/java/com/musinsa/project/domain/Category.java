package com.musinsa.project.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 카테고리")
public enum Category {
    @Schema(description = "상의", example = "TOP")
    TOP("상의"),

    @Schema(description = "아우터", example = "OUTER")
    OUTER("아우터"),

    @Schema(description = "바지", example = "PANTS")
    PANTS("바지"),

    @Schema(description = "스니커즈", example = "SNEAKERS")
    SNEAKERS("스니커즈"),

    @Schema(description = "가방", example = "BAG")
    BAG("가방"),

    @Schema(description = "모자", example = "HAT")
    HAT("모자"),

    @Schema(description = "양말", example = "SOCKS")
    SOCKS("양말"),

    @Schema(description = "액세서리", example = "ACCESSORY")
    ACCESSORY("액세서리");

    @Schema(description = "카테고리 한글명")
    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
