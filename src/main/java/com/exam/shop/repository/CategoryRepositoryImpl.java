package com.exam.shop.repository;

import com.exam.shop.domain.dto.CategoryChildDto;
import com.exam.shop.domain.dto.CategoryDto;
import com.exam.shop.domain.dto.QCategoryChildDto;
import com.exam.shop.domain.dto.QCategoryDto;
import com.exam.shop.domain.entity.Category;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.exam.shop.domain.entity.QCategory.category;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Category> findByTopCategory() {
        return queryFactory
                .selectFrom(category)
                .where(category.parent.isNull())
                .fetch();
    }

    @Override
    public List<Category> findChildrenCategory() {
        return queryFactory
                .selectFrom(category)
                .where(category.parent.isNotNull())
                .fetch();
    }

    @Override
    public List<CategoryDto> findByTopByDto() {
        return queryFactory
                .select(new QCategoryDto(
                        category.id,
                        category.categoryName
                ))
                .from(category)
                .where(category.parent.isNull())
                .fetch();
    }

    @Override
    public List<CategoryChildDto> findChildByDto() {
        return queryFactory
                .select(new QCategoryChildDto(
                        category.id,
                        category.categoryName,
                        category.parent.id
                ))
                .from(category)
                .where(category.parent.isNotNull())
                .fetch();
    }
}
