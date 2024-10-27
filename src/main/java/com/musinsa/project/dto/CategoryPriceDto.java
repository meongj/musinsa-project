package com.musinsa.project.dto;

import com.musinsa.project.domain.Category;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryPriceDto {

    private final String brandName;
    private final BigDecimal price;
    private final Category category;
}
