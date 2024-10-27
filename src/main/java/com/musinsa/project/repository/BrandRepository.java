package com.musinsa.project.repository;

import com.musinsa.project.domain.Brand;
import com.musinsa.project.domain.Category;
import com.musinsa.project.exception.BusinessException;
import com.musinsa.project.exception.ErrorCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query(
        """
        SELECT DISTINCT b FROM Brand b
        JOIN b.products p
        GROUP BY b
        HAVING COUNT(DISTINCT p.category) = :categoryCount
        AND COUNT(p) = :categoryCount
        """
    )
    List<Brand> findBrandsWithAllCategories(
        @Param("categoryCount") int categoryCount
    );

    default Brand findBrandWithAllCategoriesById(Long id) {
        return findById(id)
            .filter(brand -> {
                long distinctCategoryCount = brand
                    .getProducts()
                    .stream()
                    .map(product -> product.getCategory())
                    .distinct()
                    .count();
                return distinctCategoryCount == Category.values().length;
            })
            .orElseThrow(() -> new BusinessException(ErrorCode.BRAND_NOT_FOUND)
            );
    }
}
