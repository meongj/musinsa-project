package com.musinsa.project.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

@Getter
public class BrandPriceDto {

    private final String brandName;
    private final BigDecimal totalPrice;
    private final List<CategoryPriceDto> categoryPrices;

    public BrandPriceDto(
        String brandName,
        BigDecimal totalPrice,
        List<CategoryPriceDto> categoryPrices
    ) {
        this.brandName = brandName;
        this.totalPrice = totalPrice;
        this.categoryPrices = categoryPrices;
    }
}
