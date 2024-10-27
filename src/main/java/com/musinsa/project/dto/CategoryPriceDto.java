package com.musinsa.project.dto;

import com.musinsa.project.domain.Category;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class CategoryPriceDto {

    private final String brandName;
    private final BigDecimal price;
    private final Category category;

    public CategoryPriceDto(
        String brandName,
        BigDecimal price,
        Category category
    ) {
        this.brandName = brandName;
        this.price = price;
        this.category = category;
    }
}
