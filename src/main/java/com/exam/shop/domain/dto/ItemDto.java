package com.exam.shop.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ItemDto {

    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    private String author;

    private String isbn;

    @QueryProjection
    public ItemDto(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
