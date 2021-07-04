package com.exam.shop.repository;

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
    public Category findByTopCategory() {
        return queryFactory
                .selectFrom(category)
                .where(category.parent.isNull())
                .fetchOne();
    }

    @Override
    public List<Category> findChildrenCategory() {
        return queryFactory
                .selectFrom(category)
                .where(category.parent.isNotNull())
                .fetch();
    }
}
