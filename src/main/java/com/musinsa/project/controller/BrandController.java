package com.musinsa.project.controller;

import com.musinsa.project.domain.Category;
import com.musinsa.project.dto.BrandPriceDto;
import com.musinsa.project.dto.CategoryPriceDto;
import com.musinsa.project.dto.CategoryPriceRangeDto;
import com.musinsa.project.service.BrandService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/lowest-prices")
    public ResponseEntity<
        Map<Category, CategoryPriceDto>
    > getLowestPricesByCategory() {
        return ResponseEntity.ok(brandService.findLowestPricesByCategory());
    }

    @GetMapping("/lowest-total-price")
    public ResponseEntity<BrandPriceDto> getLowestTotalPriceBrand() {
        return ResponseEntity.ok(brandService.findLowestTotalPriceBrand());
    }

    @GetMapping("/categories/{category}/price-range")
    public ResponseEntity<CategoryPriceRangeDto> getPriceRangeByCategory(
        @PathVariable("category") String category
    ) {
        try {
            Category categoryEnum = Category.valueOf(category.toUpperCase());
            return ResponseEntity.ok(
                brandService.findPriceRangeByCategory(categoryEnum)
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
    }
}
