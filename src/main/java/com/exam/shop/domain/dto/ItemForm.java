package com.exam.shop.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ItemForm {

    private Long id;
    @NotEmpty(message = "제품명은 필수입니다.")
    private String name;
    @PositiveOrZero
    private int price;
    @PositiveOrZero
    private int stockQuantity;

    private String author;

    private String isbn;

    private Long categoryId;
}
