package com.musinsa.project.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.musinsa.project.domain.Brand;
import com.musinsa.project.domain.Category;
import com.musinsa.project.domain.Product;
import com.musinsa.project.exception.BusinessException;
import com.musinsa.project.exception.ErrorCode;
import com.musinsa.project.fixtures.BrandFixtures;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository; // @Autowired 추가

    @Test
    void shouldFindBrandWithAllCategories() {
        // given
        Brand brand = BrandFixtures.createBrandWithAllCategories();
        brandRepository.save(brand);

        // when
        List<Brand> brands = brandRepository.findBrandsWithAllCategories(
            Category.values().length
        );

        // then
        assertThat(brands).hasSize(1);
        assertThat(brands.get(0).getProducts()).hasSize(
            Category.values().length
        );
    }

    @Test
    void shouldNotFindBrandWithMissingCategories() {
        // given
        Brand brand = BrandFixtures.createBrandWithMissingCategories();
        brandRepository.save(brand);

        // when
        List<Brand> brands = brandRepository.findBrandsWithAllCategories(
            Category.values().length
        );

        // then
        assertThat(brands).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenBrandNotFound() {
        // when & then
        assertThatThrownBy(() ->
            brandRepository.findBrandWithAllCategoriesById(999L)
        )
            .isInstanceOf(BusinessException.class)
            .hasMessage(ErrorCode.BRAND_NOT_FOUND.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBrandDoesNotHaveAllCategories() {
        // given
        Brand brand = new Brand("미완성브랜드");
        brand.addProduct(
            new Product(brand, Category.TOP, BigDecimal.valueOf(10000))
        );
        
        brandRepository.save(brand);

        // when & then
        assertThatThrownBy(() ->
            brandRepository.findBrandWithAllCategoriesById(brand.getId())
        )
            .isInstanceOf(BusinessException.class)
            .hasMessage(ErrorCode.BRAND_NOT_FOUND.getMessage());
    }
}
