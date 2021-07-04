package com.exam.shop.repository;

import com.exam.shop.domain.entity.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    Category findByTopCategory();

    List<Category> findChildrenCategory();
}
