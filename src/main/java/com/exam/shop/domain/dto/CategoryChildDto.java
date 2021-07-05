package com.exam.shop.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CategoryChildDto {

    private Long childId;

    private String childName;

    private Long parentId;

    @QueryProjection
    public CategoryChildDto(Long childId, String childName, Long parentId) {
        this.childId = childId;
        this.childName = childName;
        this.parentId = parentId;
    }

    public CategoryChildDto() {

    }
}
