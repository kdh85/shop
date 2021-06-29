package com.exam.shop.domain.entity;

import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.entity.itemtype.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrdersItemTest {

    @Test
    void makeOrdersItem() {
        OrdersItem ordersItem = OrdersItem.makeOrderItem(Book.createBook(new ItemForm()),2000,10);
        assertThat(ordersItem.getOrderPrice()).isEqualTo(2000);
        assertThat(ordersItem.getOrderQuantity()).isEqualTo(10);
    }
}