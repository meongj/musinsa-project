package com.musinsa.project.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.project.domain.Category;
import com.musinsa.project.dto.BrandPriceDto;
import com.musinsa.project.dto.CategoryPriceDto;
import com.musinsa.project.dto.CategoryPriceRangeDto;
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
}
