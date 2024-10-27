package com.musinsa.project.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.musinsa.project.domain.Brand;
import com.musinsa.project.domain.Category;
import com.musinsa.project.domain.Product;
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
        Brand brand = new Brand("미완성브랜드");
        // TOP 카테고리 상품만 추가
        brand.addProduct(
            new Product(brand, Category.TOP, BigDecimal.valueOf(10000))
        );
        brandRepository.save(brand);

        // when
        List<Brand> brands = brandRepository.findBrandsWithAllCategories(
            Category.values().length
        );

        // then
        assertThat(brands).isEmpty();
    }
}
