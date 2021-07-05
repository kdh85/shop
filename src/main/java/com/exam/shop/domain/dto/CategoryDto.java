package com.exam.shop.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

@Data
public class CategoryDto {

    private Long id;

    private String categoryName;

    private List<CategoryChildDto> children;

    @Transient
    private Long parentId;

    @QueryProjection
    public CategoryDto(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public CategoryDto() {
    }
}
