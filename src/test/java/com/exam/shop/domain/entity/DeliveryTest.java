package com.exam.shop.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DeliveryTest {

    @Test
    void makeDeliveryTest() {

        Member member = Member.makeMemberWithAddress("user1",10,new Address("서울","송파구","123321"));

        Orders orders = Orders.createOrderWithAddress(member,OrderStatus.COMPLETE, member.getAddress());
        assertThat(member.getAddress()).isEqualTo(orders.getDelivery().getAddress());
    }
}