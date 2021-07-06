package com.exam.shop.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CategoryAllDto {

    private Long parentId;

    private String parentName;

    private Long childId;

    private String childName;

    @QueryProjection
    public CategoryAllDto(Long parentId, String parentName, Long childId, String childName) {
        this.parentId = parentId;
        this.parentName = parentName;
        this.childId = childId;
        this.childName = childName;
    }
}
