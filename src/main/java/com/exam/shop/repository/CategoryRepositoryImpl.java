package com.exam.shop.repository;

import com.exam.shop.domain.dto.*;
import com.exam.shop.domain.entity.Category;
import com.exam.shop.domain.entity.QCategory;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<CategoryDto> findByCategoriesDepth() {
        List<CategoryDto> byTopByDto = findByTopByDto();

        Map<Long, List<CategoryChildDto>> result = getChildrenMap();

        return getCategoryDtos(byTopByDto, result);
    }

    private List<CategoryDto> getCategoryDtos(List<CategoryDto> byTopByDto, Map<Long, List<CategoryChildDto>> result) {
        byTopByDto.forEach(o->o.setChildren(result.get(o.getId())));

        return byTopByDto;
    }

    private Map<Long, List<CategoryChildDto>> getChildrenMap() {
        Map<Long, List<CategoryChildDto>> result
                = findChildByDto().stream()
                .collect(Collectors.groupingBy(cDto -> cDto.getParentId()));
        return result;
    }

    @Override
    public List<CategoryAllDto> findByCategories() {
        QCategory categorySub = new QCategory("categorySub");

        return queryFactory
                .select(new QCategoryAllDto(
                        category.parent.id,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(categorySub.categoryName)
                                        .from(categorySub)
                                        .where(categorySub.id.eq(category.parent.id)),
                                "parentName"),
                        category.id,
                        category.categoryName
                ))
                .from(category)
                .fetch();
    }
}
