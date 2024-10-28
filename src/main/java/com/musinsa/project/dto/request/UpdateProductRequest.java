package com.musinsa.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "상품 수정 요청 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    @Schema(
        description = "수정할 상품 가격",
        example = "15000",
        required = true,
        minimum = "0"
    )
    private BigDecimal price;
}
