package com.musinsa.project.domain;

import com.musinsa.project.exception.BusinessException;
import com.musinsa.project.exception.ErrorCode;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "brands")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(
        mappedBy = "brand",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Product> products = new ArrayList<>();

    public Brand(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public Product findProduct(Long productId) {
        return products
            .stream()
            .filter(p -> Objects.equals(p.getId(), productId))
            .findFirst()
            .orElseThrow(() ->
                new BusinessException(ErrorCode.PRODUCT_NOT_FOUND)
            );
    }

    public void removeProduct(Long productId) {
        Product productToRemove = products
            .stream()
            .filter(p -> Objects.equals(p.getId(), productId)) // null-safe 비교
            .findFirst()
            .orElseThrow(() ->
                new BusinessException(ErrorCode.PRODUCT_NOT_FOUND)
            );

        products.remove(productToRemove);
    }

    public BigDecimal getProductPrice(Category category) {
        return this.products.stream()
            .filter(p -> p.getCategory() == category)
            .findFirst()
            .map(Product::getPrice)
            .orElse(BigDecimal.ZERO); // 또는 null을 반환하고 템플릿에서 처리
    }

    public Product findProductByCategory(Category category) {
        return products
            .stream()
            .filter(p -> p.getCategory() == category)
            .findFirst()
            .orElse(null);
    }
}
