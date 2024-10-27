package com.musinsa.project.dto.request;

import com.musinsa.project.domain.Category;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateProductRequest {

    private final Category category;
    private final BigDecimal price;
}
