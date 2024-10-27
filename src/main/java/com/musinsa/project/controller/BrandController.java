package com.musinsa.project.controller;

import com.musinsa.project.domain.Category;
import com.musinsa.project.dto.BrandPriceDto;
import com.musinsa.project.dto.CategoryPriceDto;
import com.musinsa.project.dto.CategoryPriceRangeDto;
import com.musinsa.project.dto.request.CreateBrandRequest;
import com.musinsa.project.dto.request.CreateProductRequest;
import com.musinsa.project.dto.request.UpdateProductRequest;
import com.musinsa.project.dto.response.BrandResponse;
import com.musinsa.project.dto.response.ProductResponse;
import com.musinsa.project.service.BrandService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(
        @RequestBody @Valid CreateBrandRequest request
    ) {
        BrandResponse response = brandService.createBrand(request);

        return ResponseEntity.created(
            URI.create("/api/brands/" + response.getId())
        ).body(response);
    }

    @PostMapping("/{brandId}/products")
    public ResponseEntity<ProductResponse> addProduct(
        @PathVariable("brandId") Long brandId,
        @RequestBody @Valid CreateProductRequest request
    ) {
        ProductResponse response = brandService.addProduct(brandId, request);
        return ResponseEntity.created(
            URI.create(
                "/api/brands/" + brandId + "/products/" + response.getId()
            )
        ).body(response);
    }

    @PutMapping("/{brandId}/products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
        @PathVariable("brandId") Long brandId,
        @PathVariable("productId") Long productId,
        @RequestBody @Valid UpdateProductRequest request
    ) {
        return ResponseEntity.ok(
            brandService.updateProduct(brandId, productId, request)
        );
    }

    @DeleteMapping("/{brandId}/products/{productId}")
    public ResponseEntity<Void> deleteProduct(
        @PathVariable("brandId") Long brandId,
        @PathVariable("productId") Long productId
    ) {
        brandService.deleteProduct(brandId, productId);
        return ResponseEntity.noContent().build();
    }
}
