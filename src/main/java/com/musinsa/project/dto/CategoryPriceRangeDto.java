package com.musinsa.project.dto;

import com.musinsa.project.domain.Category;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

@Getter
public class CategoryPriceRangeDto {

    private final Category category;
    private final CategoryPriceDto lowestPrice;
    private final CategoryPriceDto highestPrice;

    public CategoryPriceRangeDto(
        Category category,
        CategoryPriceDto lowestPrice,
        CategoryPriceDto highestPrice
    ) {
        this.category = category;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
    }
}
