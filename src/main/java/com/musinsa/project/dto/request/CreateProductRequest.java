package com.musinsa.project.dto.request;

import com.musinsa.project.domain.Category;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    private Category category;
    private BigDecimal price;
}
