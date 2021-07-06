package com.exam.shop.repository;

import com.exam.shop.domain.dto.CategoryAllDto;
import com.exam.shop.domain.dto.CategoryChildDto;
import com.exam.shop.domain.dto.CategoryDto;
import com.exam.shop.domain.entity.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> findByTopCategory();

    List<Category> findChildrenCategory();

    List<CategoryDto> findByTopByDto();

    List<CategoryChildDto> findChildByDto();

    List<CategoryDto> findByCategoriesDepth();

    List<CategoryAllDto> findByCategories();
}
