package com.exam.shop.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryTest {

    @Test
    void makeCategoryTest() {
        Category parent = new Category();
        parent.setId(1L);
        parent.setCategoryName("parent");

        Category child = new Category();
        child.setId(2L);
        child.setParent(parent);
        child.setCategoryName("child");

        assertThat(parent.getId()).isEqualTo(child.getParent().getId());
    }

    @Test
    void makeCategoryByMethodTest() {

        Category newParent = Category.createParent("parnet1");
        Category newChild = Category.createChildren("child1",newParent);

        assertThat(newParent.getCategoryName()).isEqualTo(newChild.getParent().getCategoryName());
        assertThat(newParent.getChildren()).extracting("categoryName").containsExactly(newChild.getCategoryName());
    }
}