package com.musinsa.project.service;

import com.musinsa.project.domain.Category;
import com.musinsa.project.dto.CategoryPriceDto;
import java.util.Map;

public interface BrandService {
    // 카테고리별 최저가격 브랜드와 가격 조회
    Map<Category, CategoryPriceDto> findLowestPricesByCategory();
}
