package com.musinsa.project.dto.response;

import com.musinsa.project.domain.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "브랜드 응답 DTO")
@Getter
public class BrandResponse {

    @Schema(description = "브랜드 응답 DTO")
    private final Long id;

    @Schema(description = "브랜드명", example = "NIKE")
    private final String name;

    public BrandResponse(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }
}
