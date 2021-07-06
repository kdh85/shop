package com.exam.shop.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryForm {

    @NotEmpty(message = "카테고리명은 필수입니다.")
    private String categoryName;

    private Long parentId;
}
