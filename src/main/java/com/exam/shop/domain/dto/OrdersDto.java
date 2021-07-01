package com.exam.shop.domain.dto;

import com.exam.shop.domain.entity.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdersDto {

    private Long id;

    private String name;

    private List<OrdersItemDto> orderItems;

    private OrderStatus orderStatus;

    private LocalDateTime orderDate;

    @QueryProjection
    public OrdersDto(Long id, String name, OrderStatus orderStatus, LocalDateTime orderDate) {
        this.id = id;
        this.name = name;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }
}
