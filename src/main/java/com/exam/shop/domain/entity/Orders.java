package com.exam.shop.domain.entity;

import com.exam.shop.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","address"})
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "orders_id")
    private Long id;
    //member
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member = new Member();

    @Enumerated
    private Address address;

    //orderStatus
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //Delivery
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //itemMapping
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrdersItem> ordersItemList = new ArrayList<>();

    private LocalDateTime orderDateTime;

    public static Orders createOrderWithItems(Member member, OrderStatus status, OrdersItem... ordersItem) {
        Orders newOrder = new Orders();
        newOrder.setMember(member);
        newOrder.setOrderStatus(status);
        newOrder.setDelivery(Delivery.makeDelivery(member.getAddress(), DeliveryStatus.WAIT));

        for (OrdersItem items : ordersItem) {
            newOrder.addOrderItem(items);
        }

        newOrder.setOrderStatus(OrderStatus.COMPLETE);
        newOrder.setOrderDateTime(LocalDateTime.now());
        return newOrder;
    }

    public void cancelOrder() {
        if(this.delivery.getDeliveryStatus().equals(DeliveryStatus.SEND)){
            throw new IllegalStateException("배송완료는 취소 불가능.");
        }
        this.setOrderStatus(OrderStatus.CANCEL);
        ordersItemList.forEach(ordersItem -> ordersItem.cancel(ordersItem.getOrderQuantity()));
    }

    //연관관계 편의 메소드.
    private void addOrderItem(OrdersItem items) {
        ordersItemList.add(items);
        items.setOrders(this);
    }

    //연관 관계 편의 메소드(member).
    public void setMember(Member member) {
        this.member = member;
        member.getOrdersList().add(this);
    }

    //연관 관계 편의 메소드(delivery).
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrders(this);
    }

    //주문 생성 메소드.
    public static Orders createOrder(Member member, OrderStatus status) {

        Orders newOrder = new Orders();
        newOrder.setMember(member);
        newOrder.setOrderStatus(status);
        newOrder.setDelivery(Delivery.makeDelivery(member.getAddress(), DeliveryStatus.SEND));

        return newOrder;
    }

    //주문 생성 메소드(주소 포함).
    public static Orders createOrderWithAddress(Member member, OrderStatus status, Address address) {

        Orders newOrder = new Orders();
        newOrder.setMember(member);
        newOrder.setOrderStatus(status);
        newOrder.setDelivery(Delivery.makeDelivery(address, DeliveryStatus.SEND));

        return newOrder;
    }

}
