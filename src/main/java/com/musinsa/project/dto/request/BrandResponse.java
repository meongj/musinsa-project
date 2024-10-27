package com.musinsa.project.dto.request;

import com.musinsa.project.domain.Brand;
import lombok.Getter;

@Getter
public class BrandResponse {

    private final Long id;
    private final String name;

    public BrandResponse(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }
}
