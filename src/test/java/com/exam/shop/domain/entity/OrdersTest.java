package com.exam.shop.domain.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrdersTest {
    @Test
    void makeOrder() {
        Member member = Member.makeMember("user1",10);
        Orders orders = Orders.createOrder(member,OrderStatus.COMPLETE);

        assertThat(member.getUsername()).isEqualTo(orders.member.getUsername());
        assertThat(member.getAge()).isEqualTo(orders.member.getAge());
        assertThat(orders.getOrderStatus()).isEqualTo(OrderStatus.COMPLETE);
    }

}