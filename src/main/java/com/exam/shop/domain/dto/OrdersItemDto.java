package com.exam.shop.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class OrdersItemDto {

    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;

    @QueryProjection
    public OrdersItemDto(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }

}
