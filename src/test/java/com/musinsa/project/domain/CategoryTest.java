package com.musinsa.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void categoryShouldHaveCorrectDisplayName() {
        assertThat(Category.TOP.getDisplayName()).isEqualTo("상의");
        assertThat(Category.OUTER.getDisplayName()).isEqualTo("아우터");
    }
}
