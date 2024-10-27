package com.musinsa.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BrandTest {

    @Test
    void brandShouldHaveNameAndProducts() {
        // given
        String brandName = "테스트브랜드";

        // when
        Brand brand = new Brand(brandName);

        // then
        assertThat(brand.getName()).isEqualTo(brandName);
        assertThat(brand.getProducts()).isEmpty(); // 초기에는 상품 없음
    }

    @Test
    void shouldAddProductToBrand() {
        // given
        Brand brand = new Brand("테스트브랜드");
        Product product = new Product(
            brand,
            Category.TOP,
            BigDecimal.valueOf(10000)
        );

        // when
        brand.addProduct(product);

        // then
        assertThat(brand.getProducts()).hasSize(1).contains(product);
    }
}
