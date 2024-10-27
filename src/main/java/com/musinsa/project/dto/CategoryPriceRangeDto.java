package com.musinsa.project.dto;

import com.musinsa.project.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryPriceRangeDto {

    private final Category category;
    private final CategoryPriceDto lowestPrice;
    private final CategoryPriceDto highestPrice;
}
