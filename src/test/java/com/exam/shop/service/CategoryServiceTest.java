package com.exam.shop.service;

import com.exam.shop.domain.dto.CategoryChildDto;
import com.exam.shop.domain.dto.CategoryDto;
import com.exam.shop.domain.entity.Category;
import com.exam.shop.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Category product = Category.createParent("제품");
        categoryRepository.save(product);
        Category book = Category.createChildren("책", product);
        categoryRepository.save(book);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void createParentCategoryTest() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName("제품1");

        //Category product = Category.createParent("제품1");
        Category product = Category.createParent(categoryDto.getCategoryName());
        categoryRepository.save(product);
    }

    @Test
    void searchCategoryTest() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName("제품");

        //Category findOne = categoryRepository.findByCategoryName("제품");
        Category findOne = categoryRepository.findByCategoryName(categoryDto.getCategoryName());
        assertThat(findOne.getCategoryName()).isEqualTo("제품");
    }

    @Test
    void createChildCategoryTest() {

        Category findOne = categoryRepository.findByCategoryName("제품군");
        Category book = Category.createChildren("책", findOne);
        
        assertThat(book.getParent().getCategoryName()).isEqualTo("제품군");
    }

    @Test
    void searchTopCategoryTest() {
        List<Category> topCategory = categoryRepository.findByTopCategory();
        assertThat(topCategory).extracting("categoryName").isEqualTo("제품");
    }

    @Test
    void searchChildrenCategoryTest() {
        List<Category> childrenCategory = categoryRepository.findChildrenCategory();
        assertThat(childrenCategory).extracting("categoryName").containsExactly("책");
    }

    @Test
    void searchTopByDtoTest() {
        List<CategoryDto> topByDto = categoryRepository.findByTopByDto();
        assertThat(topByDto).extracting("categoryName").containsExactly("제품");
    }

    @Test
    void searchChildByDtoTest() {
        List<CategoryChildDto> childByDto = categoryRepository.findChildByDto();
        assertThat(childByDto).extracting("childName").containsExactly("책");
    }
}