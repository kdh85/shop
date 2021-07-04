package com.exam.shop.repository;

import com.exam.shop.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    Category findByCategoryName(String categoryName);
}
