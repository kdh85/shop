package com.exam.shop.repository;

import com.exam.shop.domain.dto.OrderSearchCondition;
import com.exam.shop.domain.dto.OrdersDto;
import com.exam.shop.domain.entity.OrdersItem;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;

import java.util.List;

public interface OrdersRepositoryCustom {
    List<OrdersDto> searchByCondition(OrderSearchCondition condition);
    QueryResults<Tuple> searchOrders(OrderSearchCondition condition);
    List<OrdersItem> searchOrdersItem(Long orderId);
}
