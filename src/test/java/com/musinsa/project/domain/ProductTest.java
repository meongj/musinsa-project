package com.musinsa.project.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy; // 이 import 추가

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void productShouldHaveRequiredProperties() {
        // given
        Brand brand = new Brand("테스트브랜드");
        Category category = Category.TOP;
        BigDecimal price = BigDecimal.valueOf(10000);

        // when
        Product product = new Product(brand, category, price);

        // then
        assertThat(product.getBrand()).isEqualTo(brand);
        assertThat(product.getCategory()).isEqualTo(category);
        assertThat(product.getPrice()).isEqualByComparingTo(price);
    }

    @Test
    void shouldNotCreateProductWithNegativePrice() {
        // given
        Brand brand = new Brand("테스트브랜드");
        BigDecimal negativePrice = BigDecimal.valueOf(-1000);

        // when & then
        assertThatThrownBy(() -> new Product(brand, Category.TOP, negativePrice)
        )
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("가격은 0보다 커야 합니다");
    }
}
