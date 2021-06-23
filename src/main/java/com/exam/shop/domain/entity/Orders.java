package com.exam.shop.domain.entity;

import com.exam.shop.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

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
