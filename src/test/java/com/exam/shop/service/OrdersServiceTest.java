package com.exam.shop.service;

import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.dto.MemberForm;
import com.exam.shop.domain.search.OrderSearchCondition;
import com.exam.shop.domain.dto.OrdersDto;
import com.exam.shop.domain.entity.Item;
import com.exam.shop.domain.entity.Member;
import com.exam.shop.domain.entity.OrderStatus;
import com.exam.shop.domain.entity.itemtype.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
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

    @Autowired
    private EntityManager entityManager;

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
    void ordersCreateAndSearchTest() {
        System.out.println("----------ordersCreateTest call---------");
        Member findMember = memberService.findByUsername("user0");

        Optional<Book> findItem = itemService.findByBook("book1");

        Long orderId = ordersService.ordersCreate(findMember.getId(), findItem.get().getId(), 10);

        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setOrderId(orderId);
        condition.setMemberName("user0");
        condition.setOrderStatus(OrderStatus.COMPLETE);

        List<OrdersDto> ordersDtos = ordersService.searchOrdersWithItems(condition);
        for (OrdersDto ordersDto : ordersDtos) {
            assertThat(orderId).isEqualTo(ordersDto.getId());
        }
        assertThat(ordersDtos).extracting(OrdersDto::getName).containsExactly(condition.getMemberName());

    }

    @Test
    void orderCancelTest() {
        System.out.println("----------orderCancelTest call---------");
        //주문생성.
        Optional<Member> findMember = memberService.findById(1L);

        Optional<Item> findItem = itemService.findById(1L);

        if (findMember.isPresent() && findItem.isPresent()) {
            Long orderId = ordersService.ordersCreate(findMember.get().getId(), findItem.get().getId(), 10);
            entityManager.flush();
            entityManager.clear();
            ordersService.ordersCancel(orderId);
        }


        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setMemberName("user0");
        condition.setOrderStatus(OrderStatus.CANCEL);

        List<OrdersDto> ordersDtos = ordersService.searchOrdersWithItems(condition);
//        for (OrdersDto ordersDto : ordersDtos) {
//            System.out.println("ordersDto = " + ordersDto);
//        }
//        assertThat(ordersDtos.get(0).getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
    }
}