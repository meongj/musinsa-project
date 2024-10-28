package com.musinsa.project.dto.response;

import com.musinsa.project.domain.Category;
import com.musinsa.project.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;

@Schema(description = "상품 응답 DTO")
@Getter
public class ProductResponse {

    @Schema(description = "상품 ID", example = "1")
    private final Long id;

    @Schema(description = "브랜드명", example = "NIKE")
    private final String brandName;

    @Schema(description = "카테고리", example = "TOP")
    private final Category category;

    @Schema(description = "가격", example = "10000")
    private final BigDecimal price;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.brandName = product.getBrand().getName();
        this.category = product.getCategory();
        this.price = product.getPrice();
    }
}
