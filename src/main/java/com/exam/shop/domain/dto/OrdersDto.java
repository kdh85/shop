package com.exam.shop.domain.dto;

import com.exam.shop.domain.entity.OrderStatus;
import com.exam.shop.domain.entity.OrdersItem;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrdersDto {

    private Long id;

    private String name;

    private List<OrdersItem> orderItems = new ArrayList<>();

    private OrderStatus orderStatus;

    private LocalDateTime orderDate;

    @QueryProjection
    public OrdersDto(Long id, String name, List<OrdersItem> orderItems, OrderStatus orderStatus, LocalDateTime orderDate) {
        this.id = id;
        this.name = name;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }
}
