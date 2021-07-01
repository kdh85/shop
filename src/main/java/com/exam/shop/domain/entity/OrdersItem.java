package com.exam.shop.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdersItem {

    @Id
    @GeneratedValue
    @Column(name = "orders_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;

    private int orderQuantity;

    //주문제품 정보 생성 메소드.
    public static OrdersItem makeOrderItem(Item item, int orderPrice, int orderQuantity) {
        OrdersItem newOrdersItem = new OrdersItem();
        newOrdersItem.setItem(item);
        newOrdersItem.setOrderPrice(orderPrice);
        newOrdersItem.setOrderQuantity(orderQuantity);

        item.removeQuantity(orderQuantity);
        return newOrdersItem;
    }

    public void cancel(int orderQuantity) {
        item.addQuantity(orderQuantity);
    }
}
