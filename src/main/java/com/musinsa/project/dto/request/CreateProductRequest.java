package com.musinsa.project.dto.request;

import com.musinsa.project.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "상품 생성 요청 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    @Schema(
        description = "상품 카테고리 (TOP, OUTER, PANTS, SNEAKERS, BAG, HAT, SOCKS, ACCESSORY)",
        example = "TOP",
        required = true,
        allowableValues = {
            "TOP",
            "OUTER",
            "PANTS",
            "SNEAKERS",
            "BAG",
            "HAT",
            "SOCKS",
            "ACCESSORY",
        }
    )
    private Category category;

    @Schema(
        description = "상품 가격",
        example = "10000",
        required = true,
        minimum = "0"
    )
    private BigDecimal price;
}
