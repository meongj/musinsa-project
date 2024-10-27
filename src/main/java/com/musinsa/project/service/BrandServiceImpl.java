package com.musinsa.project.service;

import com.musinsa.project.domain.Brand;
import com.musinsa.project.domain.Category;
import com.musinsa.project.domain.Product;
import com.musinsa.project.dto.CategoryPriceDto;
import com.musinsa.project.exception.BusinessException;
import com.musinsa.project.exception.ErrorCode;
import com.musinsa.project.repository.BrandRepository;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Map<Category, CategoryPriceDto> findLowestPricesByCategory() {
        List<Brand> brands = brandRepository.findBrandsWithAllCategories(
            Category.values().length
        );

        return Arrays.stream(Category.values()).collect(
            Collectors.toMap(
                category -> category,
                category -> findLowestPriceForCategory(brands, category)
            )
        );
    }

    private CategoryPriceDto findLowestPriceForCategory(
        List<Brand> brands,
        Category category
    ) {
        return brands
            .stream()
            .map(brand ->
                new CategoryPriceDto(
                    brand.getName(),
                    brand
                        .getProducts()
                        .stream()
                        .filter(p -> p.getCategory() == category)
                        .findFirst()
                        .map(Product::getPrice)
                        .orElseThrow(() ->
                            new BusinessException(ErrorCode.PRODUCT_NOT_FOUND)
                        ),
                    category
                )
            )
            .min(Comparator.comparing(CategoryPriceDto::getPrice))
            .orElseThrow(() ->
                new BusinessException(ErrorCode.PRODUCT_NOT_FOUND)
            );
    }
}
