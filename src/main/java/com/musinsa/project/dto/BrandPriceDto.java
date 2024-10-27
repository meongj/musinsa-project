package com.musinsa.project.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BrandPriceDto {

    private final String brandName;
    private final BigDecimal totalPrice;
    private final List<CategoryPriceDto> categoryPrices;
}
