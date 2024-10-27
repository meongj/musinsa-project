package com.musinsa.project.repository;

import com.musinsa.project.domain.Brand;
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
}
