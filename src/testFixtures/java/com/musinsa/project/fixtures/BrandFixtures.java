package com.musinsa.project.fixtures;

import com.musinsa.project.domain.Brand;
import com.musinsa.project.domain.Category;
import com.musinsa.project.domain.Product;
import java.math.BigDecimal;

public class BrandFixtures {

    public static Brand createBrandWithAllCategories() {
        Brand brand = new Brand("테스트브랜드");

        for (Category category : Category.values()) {
            Product product = new Product(
                brand,
                category,
                BigDecimal.valueOf(10000)
            );
            brand.addProduct(product);
        }

        return brand;
    }

    public static Brand createBrandWithMissingCategories() {
        Brand brand = new Brand("미완성브랜드");
        brand.addProduct(
            new Product(brand, Category.TOP, BigDecimal.valueOf(10000))
        );
        return brand;
    }
}
