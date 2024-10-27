package com.musinsa.project.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.musinsa.project.fixtures.BrandFixtures;
import com.musinsa.project.repository.BrandRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

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

    @Test
    void shouldFindPriceRangeByCategory() {
        // given
        Brand brandA = BrandFixtures.createBrandWithSpecificPrice(
            "A",
            BigDecimal.valueOf(10000)
        );
        Brand brandB = BrandFixtures.createBrandWithSpecificPrice(
            "B",
            BigDecimal.valueOf(8000)
        );
        Brand brandC = BrandFixtures.createBrandWithSpecificPrice(
            "C",
            BigDecimal.valueOf(12000)
        );

        when(
            brandRepository.findBrandsWithAllCategories(
                Category.values().length
            )
        ).thenReturn(Arrays.asList(brandA, brandB, brandC));

        // when
        CategoryPriceRangeDto result = brandService.findPriceRangeByCategory(
            Category.TOP
        );

        // then
        assertThat(result.getCategory()).isEqualTo(Category.TOP);

        assertThat(result.getLowestPrice().getBrandName()).isEqualTo("B");
        assertThat(result.getLowestPrice().getPrice()).isEqualByComparingTo(
            BigDecimal.valueOf(8000)
        );

        assertThat(result.getHighestPrice().getBrandName()).isEqualTo("C");
        assertThat(result.getHighestPrice().getPrice()).isEqualByComparingTo(
            BigDecimal.valueOf(12000)
        );
    }

    @Test
    void shouldCreateBrand() {
        // given
        CreateBrandRequest request = new CreateBrandRequest("테스트브랜드");
        Brand brand = new Brand(request.getName());
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        // when
        BrandResponse response = brandService.createBrand(request);

        // then
        assertThat(response.getName()).isEqualTo("테스트브랜드");
        verify(brandRepository).save(any(Brand.class));
    }

    @Test
    void shouldAddProductToBrand() {
        // given
        Long brandId = 1L;
        CreateProductRequest request = new CreateProductRequest(
            Category.TOP,
            BigDecimal.valueOf(10000)
        );

        Brand brand = new Brand("테스트브랜드");
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        // when
        ProductResponse response = brandService.addProduct(brandId, request);

        // then
        assertThat(response.getCategory()).isEqualTo(Category.TOP);
        assertThat(response.getPrice()).isEqualByComparingTo(
            BigDecimal.valueOf(10000)
        );
        verify(brandRepository).save(any(Brand.class));
    }

    @Test
    void shouldUpdateProduct() {
        // given
        Long brandId = 1L;
        Long productId = 1L;
        UpdateProductRequest request = new UpdateProductRequest(
            BigDecimal.valueOf(20000)
        );

        Brand brand = new Brand("테스트브랜드");
        Product product = new Product(
            brand,
            Category.TOP,
            BigDecimal.valueOf(10000)
        );
        ReflectionTestUtils.setField(product, "id", productId); // ID 설정

        brand.addProduct(product);
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));

        // when
        ProductResponse response = brandService.updateProduct(
            brandId,
            productId,
            request
        );

        // then
        assertThat(response.getPrice()).isEqualByComparingTo(
            BigDecimal.valueOf(20000)
        );
        verify(brandRepository).save(any(Brand.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        // given
        Long brandId = 1L;
        Long nonExistentProductId = 999L;
        UpdateProductRequest request = new UpdateProductRequest(
            BigDecimal.valueOf(20000)
        );

        Brand brand = new Brand("테스트브랜드");
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));

        // when & then
        assertThatThrownBy(() ->
            brandService.updateProduct(brandId, nonExistentProductId, request)
        )
            .isInstanceOf(BusinessException.class)
            .hasMessage(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

    @Test
    void shouldDeleteProduct() {
        // given
        Long brandId = 1L;
        Long productId = 1L;

        Brand brand = new Brand("테스트브랜드");
        Product product = new Product(
            brand,
            Category.TOP,
            BigDecimal.valueOf(10000)
        );

        // Product가 DB에 저장되었다고 가정하고 ID 설정
        ReflectionTestUtils.setField(product, "id", productId);

        brand.addProduct(product);
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));

        // when
        brandService.deleteProduct(brandId, productId);

        // then
        verify(brandRepository).save(any(Brand.class));
        assertThat(brand.getProducts()).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentProduct() {
        // given
        Long brandId = 1L;
        Long nonExistentProductId = 999L;

        Brand brand = new Brand("테스트브랜드");
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));

        // when & then
        assertThatThrownBy(() ->
            brandService.deleteProduct(brandId, nonExistentProductId)
        )
            .isInstanceOf(BusinessException.class)
            .hasMessage(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }
}
