package com.musinsa.project.dto.request;

import com.musinsa.project.domain.Category;
import com.musinsa.project.domain.Product;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;
    private final String brandName;
    private final Category category;
    private final BigDecimal price;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.brandName = product.getBrand().getName();
        this.category = product.getCategory();
        this.price = product.getPrice();
    }
}
