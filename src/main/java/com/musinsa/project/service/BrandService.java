package com.musinsa.project.service;

import com.musinsa.project.domain.Category;
import com.musinsa.project.dto.BrandPriceDto;
import com.musinsa.project.dto.CategoryPriceDto;
import com.musinsa.project.dto.CategoryPriceRangeDto;
import com.musinsa.project.dto.request.CreateBrandRequest;
import com.musinsa.project.dto.request.CreateProductRequest;
import com.musinsa.project.dto.request.UpdateProductRequest;
import com.musinsa.project.dto.response.BrandResponse;
import com.musinsa.project.dto.response.ProductResponse;
import java.util.Map;

public interface BrandService {
    // 카테고리별 최저가격 브랜드와 가격 조회
    Map<Category, CategoryPriceDto> findLowestPricesByCategory();

    //단일 브랜드로 전체 카테고리 상품을 구매할 때 최저가격 브랜드 조회
    BrandPriceDto findLowestTotalPriceBrand();

    // 특정 카테고리의 최저/최고가 브랜드 조회
    CategoryPriceRangeDto findPriceRangeByCategory(Category category);

    BrandResponse createBrand(CreateBrandRequest request);
    ProductResponse addProduct(Long brandId, CreateProductRequest request);
    ProductResponse updateProduct(
        Long brandId,
        Long productId,
        UpdateProductRequest request
    );
    void deleteProduct(Long brandId, Long productId);
}
