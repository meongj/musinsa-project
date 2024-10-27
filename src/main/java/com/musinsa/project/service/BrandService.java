package com.musinsa.project.service;

import com.musinsa.project.domain.Category;
import com.musinsa.project.dto.BrandPriceDto;
import com.musinsa.project.dto.CategoryPriceDto;
import java.util.Map;

public interface BrandService {
    // 카테고리별 최저가격 브랜드와 가격 조회
    Map<Category, CategoryPriceDto> findLowestPricesByCategory();

    //단일 브랜드로 전체 카테고리 상품을 구매할 때 최저가격 브랜드 조회
    BrandPriceDto findLowestTotalPriceBrand();
}