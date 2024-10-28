package com.musinsa.project.controller;

import com.musinsa.project.domain.Category;
import com.musinsa.project.dto.BrandPriceDto;
import com.musinsa.project.dto.CategoryPriceDto;
import com.musinsa.project.dto.CategoryPriceRangeDto;
import com.musinsa.project.dto.request.CreateBrandRequest;
import com.musinsa.project.dto.request.CreateProductRequest;
import com.musinsa.project.dto.request.UpdateProductRequest;
import com.musinsa.project.dto.response.BrandResponse;
import com.musinsa.project.dto.response.ErrorResponse;
import com.musinsa.project.dto.response.ProductResponse;
import com.musinsa.project.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Brand API", description = "브랜드와 상품 관리를 위한 API")
public class BrandController {

    private final BrandService brandService;

    @Operation(
        summary = "카테고리별 최저가격 브랜드와 가격 조회",
        description = "각 카테고리별로 가장 낮은 가격을 제공하는 브랜드와 해당 가격을 조회합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                useReturnTypeSchema = true
            ),
        }
    )
    @GetMapping("/lowest-prices")
    public ResponseEntity<
        Map<Category, CategoryPriceDto>
    > getLowestPricesByCategory() {
        return ResponseEntity.ok(brandService.findLowestPricesByCategory());
    }

    @Operation(
        summary = "단일 브랜드 최저가격 조회",
        description = "모든 카테고리 상품을 단일 브랜드에서 구매할 때 가장 저렴한 브랜드와 총액을 조회합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                useReturnTypeSchema = true
            ),
            @ApiResponse(
                responseCode = "404",
                description = "브랜드를 찾을 수 없음",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
        }
    )
    @GetMapping("/lowest-total-price")
    public ResponseEntity<BrandPriceDto> getLowestTotalPriceBrand() {
        return ResponseEntity.ok(brandService.findLowestTotalPriceBrand());
    }

    @Operation(
        summary = "카테고리별 가격 범위 조회",
        description = "지정된 카테고리의 최저가와 최고가 브랜드 정보를 조회합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                useReturnTypeSchema = true
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 카테고리",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
        }
    )
    @GetMapping("/categories/{category}/price-range")
    public ResponseEntity<CategoryPriceRangeDto> getPriceRangeByCategory(
        @Parameter(
            description = "조회할 카테고리명",
            required = true,
            example = "TOP"
        ) @PathVariable("category") String category
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

    @Operation(
        summary = "새로운 브랜드 등록",
        description = "새로운 브랜드를 시스템에 등록합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(
                responseCode = "201",
                description = "브랜드 생성 성공",
                useReturnTypeSchema = true
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
        }
    )
    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(
        @Parameter(
            description = "생성할 브랜드 정보",
            required = true
        ) @RequestBody @Valid CreateBrandRequest request
    ) {
        BrandResponse response = brandService.createBrand(request);

        return ResponseEntity.created(
            URI.create("/api/brands/" + response.getId())
        ).body(response);
    }

    @Operation(
        summary = "브랜드에 상품 추가",
        description = "기존 브랜드에 새로운 상품을 추가합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(
                responseCode = "201",
                description = "상품 추가 성공",
                useReturnTypeSchema = true
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "브랜드를 찾을 수 없음",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
        }
    )
    @PostMapping("/{brandId}/products")
    public ResponseEntity<ProductResponse> addProduct(
        @Parameter(description = "브랜드 ID", required = true) @PathVariable(
            "brandId"
        ) Long brandId,
        @Parameter(
            description = "추가할 상품 정보",
            required = true
        ) @RequestBody @Valid CreateProductRequest request
    ) {
        ProductResponse response = brandService.addProduct(brandId, request);
        return ResponseEntity.created(
            URI.create(
                "/api/brands/" + brandId + "/products/" + response.getId()
            )
        ).body(response);
    }

    @Operation(
        summary = "상품 정보 수정",
        description = "기존 상품의 정보를 수정합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(
                responseCode = "200",
                description = "수정 성공",
                useReturnTypeSchema = true
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "브랜드 또는 상품을 찾을 수 없음",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
        }
    )
    @PutMapping("/{brandId}/products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
        @Parameter(description = "브랜드 ID", required = true) @PathVariable(
            "brandId"
        ) Long brandId,
        @Parameter(description = "상품 ID", required = true) @PathVariable(
            "productId"
        ) Long productId,
        @Parameter(
            description = "수정할 상품 정보",
            required = true
        ) @RequestBody @Valid UpdateProductRequest request
    ) {
        return ResponseEntity.ok(
            brandService.updateProduct(brandId, productId, request)
        );
    }

    @Operation(
        summary = "상품 삭제",
        description = "브랜드의 특정 상품을 삭제합니다."
    )
    @ApiResponses(
        {
            @ApiResponse(
                responseCode = "204",
                description = "삭제 성공",
                useReturnTypeSchema = true
            ),
            @ApiResponse(
                responseCode = "404",
                description = "브랜드 또는 상품을 찾을 수 없음",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
        }
    )
    @DeleteMapping("/{brandId}/products/{productId}")
    public ResponseEntity<Void> deleteProduct(
        @Parameter(description = "브랜드 ID", required = true) @PathVariable(
            "brandId"
        ) Long brandId,
        @Parameter(description = "상품 ID", required = true) @PathVariable(
            "productId"
        ) Long productId
    ) {
        brandService.deleteProduct(brandId, productId);
        return ResponseEntity.noContent().build();
    }
}
