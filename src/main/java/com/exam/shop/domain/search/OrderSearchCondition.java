package com.exam.shop.domain.search;

import com.exam.shop.domain.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchCondition {

    private String memberName;

    private OrderStatus orderStatus;

    private Long orderId;
}
