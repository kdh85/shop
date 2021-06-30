package com.exam.shop.service;

import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.dto.MemberForm;
import com.exam.shop.domain.entity.Item;
import com.exam.shop.domain.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrdersServiceTest {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ItemService itemService;

    @BeforeEach
    void setUp() {

        MemberForm newMember = new MemberForm();
        newMember.setName("user0");
        newMember.setCity("서울");
        newMember.setStreet("송파");
        newMember.setZipcode("123321");
        memberService.join(newMember);

        ItemForm itemForm = new ItemForm();
        itemForm.setName("book1");
        itemForm.setPrice(10000);
        itemForm.setStockQuantity(100);
        itemForm.setAuthor("author");
        itemForm.setIsbn("isbn0001");

        itemService.itemCreate(itemForm);

    }

    @Test
    void ordersCreateTest() {
        Optional<Member> findMember = memberService.findById(1L);

        Optional<Item> findItem = itemService.findById(1L);

        if(findMember.isPresent() && findItem.isPresent()){
            Long orderId = ordersService.ordersCreate(findMember.get().getId(), findItem.get().getId(), 10);

            assertThat(orderId).isEqualTo(1L);
        }
    }
}