package com.musinsa.project.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.project.domain.Brand;
import com.musinsa.project.domain.Category;
import com.musinsa.project.domain.Product;
import com.musinsa.project.dto.BrandPriceDto;
import com.musinsa.project.dto.CategoryPriceDto;
import com.musinsa.project.dto.CategoryPriceRangeDto;
import com.musinsa.project.dto.request.CreateBrandRequest;
import com.musinsa.project.dto.request.CreateProductRequest;
import com.musinsa.project.dto.request.UpdateProductRequest;
import com.musinsa.project.dto.response.BrandResponse;
import com.musinsa.project.dto.response.ProductResponse;
import com.musinsa.project.exception.BusinessException;
import com.musinsa.project.exception.ErrorCode;
import com.musinsa.project.service.BrandService;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BrandController.class)
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BrandService brandService;

    @Test
    void shouldReturnLowestPricesByCategory() throws Exception {
        // given
        Map<Category, CategoryPriceDto> lowestPrices = new HashMap<>();
        lowestPrices.put(
            Category.TOP,
            new CategoryPriceDto(
                "브랜드A",
                BigDecimal.valueOf(10000),
                Category.TOP
            )
        );

        when(brandService.findLowestPricesByCategory()).thenReturn(
            lowestPrices
        );

        // when & then
        mockMvc
            .perform(get("/api/brands/lowest-prices"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.TOP.brandName").value("브랜드A"))
            .andExpect(jsonPath("$.TOP.price").value(10000));
    }

    @Test
    void shouldReturnLowestTotalPriceBrand() throws Exception {
        // given
        BrandPriceDto brandPrice = new BrandPriceDto(
            "브랜드A",
            BigDecimal.valueOf(80000),
            Collections.emptyList()
        );

        when(brandService.findLowestTotalPriceBrand()).thenReturn(brandPrice);

        // when & then
        mockMvc
            .perform(get("/api/brands/lowest-total-price"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.brandName").value("브랜드A"))
            .andExpect(jsonPath("$.totalPrice").value(80000));
    }

    @Test
    void shouldReturnPriceRangeByCategory() throws Exception {
        // given
        CategoryPriceRangeDto priceRange = new CategoryPriceRangeDto(
            Category.TOP,
            new CategoryPriceDto(
                "브랜드A",
                BigDecimal.valueOf(10000),
                Category.TOP
            ),
            new CategoryPriceDto(
                "브랜드B",
                BigDecimal.valueOf(20000),
                Category.TOP
            )
        );

        when(brandService.findPriceRangeByCategory(Category.TOP)).thenReturn(
            priceRange
        );

        // when & then
        mockMvc
            .perform(get("/api/brands/categories/TOP/price-range"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.category").value("TOP"))
            .andExpect(jsonPath("$.lowestPrice.brandName").value("브랜드A"))
            .andExpect(jsonPath("$.highestPrice.brandName").value("브랜드B"));
    }

    @Test
    void shouldHandleBusinessException() throws Exception {
        // given
        when(brandService.findPriceRangeByCategory(Category.TOP)).thenThrow(
            new BusinessException(ErrorCode.BRAND_NOT_FOUND)
        );

        // when & then
        mockMvc
            .perform(get("/api/brands/categories/TOP/price-range"))
            .andExpect(status().isBadRequest())
            .andExpect(
                jsonPath("$.code").value(ErrorCode.BRAND_NOT_FOUND.getCode())
            )
            .andExpect(
                jsonPath("$.message").value(
                    ErrorCode.BRAND_NOT_FOUND.getMessage()
                )
            );
    }

    @Test
    void shouldHandleInvalidCategoryException() throws Exception {
        // when & then
        mockMvc
            .perform(get("/api/brands/categories/INVALID/price-range"))
            .andExpect(status().isBadRequest())
            .andExpect(
                jsonPath("$.message").value("Invalid category: INVALID")
            );
    }

    @Test
    void shouldCreateBrand() throws Exception {
        // given
        CreateBrandRequest request = new CreateBrandRequest("새브랜드");

        Brand brand = new Brand("새브랜드");
        ReflectionTestUtils.setField(brand, "id", 1L);

        BrandResponse response = new BrandResponse(brand);

        when(
            brandService.createBrand(any(CreateBrandRequest.class))
        ).thenReturn(response);

        // when & then
        mockMvc
            .perform(
                post("/api/brands")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("새브랜드"));
    }

    @Test
    void shouldAddProduct() throws Exception {
        // given
        Long brandId = 1L;
        CreateProductRequest request = new CreateProductRequest(
            Category.TOP,
            BigDecimal.valueOf(10000)
        );

        Brand brand = new Brand("브랜드A");
        Product product = new Product(
            brand,
            Category.TOP,
            BigDecimal.valueOf(10000)
        );
        ReflectionTestUtils.setField(product, "id", 1L); // ID 설정

        ProductResponse response = new ProductResponse(product);

        when(
            brandService.addProduct(
                eq(brandId),
                any(CreateProductRequest.class)
            )
        ).thenReturn(response);

        // when & then
        mockMvc
            .perform(
                post("/api/brands/{brandId}/products", brandId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.category").value("TOP"))
            .andExpect(jsonPath("$.price").value(10000));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        // given
        Long brandId = 1L;
        Long productId = 1L;
        UpdateProductRequest request = new UpdateProductRequest(
            BigDecimal.valueOf(20000)
        );

        Brand brand = new Brand("브랜드A");

        Product product = new Product(
            brand,
            Category.TOP,
            BigDecimal.valueOf(20000)
        );
        ReflectionTestUtils.setField(product, "id", productId); // ID 설정
        ProductResponse response = new ProductResponse(product);

        when(
            brandService.updateProduct(
                eq(brandId),
                eq(productId),
                any(UpdateProductRequest.class)
            )
        ).thenReturn(response);

        // when & then
        mockMvc
            .perform(
                put(
                    "/api/brands/{brandId}/products/{productId}",
                    brandId,
                    productId
                )
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.price").value(20000));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        // given
        Long brandId = 1L;
        Long productId = 1L;

        // when & then
        mockMvc
            .perform(
                delete(
                    "/api/brands/{brandId}/products/{productId}",
                    brandId,
                    productId
                )
            )
            .andExpect(status().isNoContent());
    }
}
