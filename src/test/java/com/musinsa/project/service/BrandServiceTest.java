package com.musinsa.project.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.musinsa.project.domain.Brand;
import com.musinsa.project.domain.Category;
import com.musinsa.project.dto.BrandPriceDto;
import com.musinsa.project.dto.CategoryPriceDto;
import com.musinsa.project.fixtures.BrandFixtures;
import com.musinsa.project.repository.BrandRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    private BrandService brandService; // 인터페이스 사용

    @BeforeEach
    void setUp() {
        brandService = new BrandServiceImpl(brandRepository); // 테스트 전에 구현체 생성
    }

    @Test
    void shouldFindLowestPriceByCategory() {
        // given
        Brand brandA = BrandFixtures.createBrandWithSpecificPrice(
            "A",
            BigDecimal.valueOf(10000)
        );
        Brand brandB = BrandFixtures.createBrandWithSpecificPrice(
            "B",
            BigDecimal.valueOf(9000)
        );

        when(
            brandRepository.findBrandsWithAllCategories(
                Category.values().length
            )
        ).thenReturn(Arrays.asList(brandA, brandB));

        // when
        Map<Category, CategoryPriceDto> result =
            brandService.findLowestPricesByCategory();

        // then
        assertThat(result)
            .hasSize(Category.values().length)
            .allSatisfy((category, dto) -> {
                assertThat(dto.getBrandName()).isEqualTo("B");
                assertThat(dto.getPrice()).isEqualByComparingTo(
                    BigDecimal.valueOf(9000)
                );
            });
    }

    @Test
    void shouldFindBrandWithLowestTotalPrice() {
        // given
        Brand brandA = BrandFixtures.createBrandWithSpecificPrice(
            "A",
            BigDecimal.valueOf(10000)
        ); // 총 80,000
        Brand brandB = BrandFixtures.createBrandWithSpecificPrice(
            "B",
            BigDecimal.valueOf(9000)
        ); // 총 72,000

        when(
            brandRepository.findBrandsWithAllCategories(
                Category.values().length
            )
        ).thenReturn(Arrays.asList(brandA, brandB));

        // when
        BrandPriceDto result = brandService.findLowestTotalPriceBrand();

        // then
        assertThat(result.getBrandName()).isEqualTo("B");
        assertThat(result.getTotalPrice()).isEqualByComparingTo(
            BigDecimal.valueOf(72000)
        );
        assertThat(result.getCategoryPrices())
            .hasSize(Category.values().length)
            .allSatisfy(categoryPrice ->
                assertThat(categoryPrice.getPrice()).isEqualByComparingTo(
                    BigDecimal.valueOf(9000)
                )
            );
    }
}
