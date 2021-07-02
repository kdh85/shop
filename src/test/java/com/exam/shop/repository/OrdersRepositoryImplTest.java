package com.exam.shop.repository;

import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.dto.OrderSearchCondition;
import com.exam.shop.domain.dto.OrdersDto;
import com.exam.shop.domain.dto.OrdersItemDto;
import com.exam.shop.domain.entity.*;
import com.exam.shop.domain.entity.itemtype.Book;
import com.exam.shop.service.OrdersService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrdersRepositoryImplTest {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {

        memberRepository.save(Member.makeMember("user0", 11));

        ItemForm itemForm = new ItemForm();
        itemForm.setName("testbook1");
        itemForm.setPrice(10000);
        itemForm.setStockQuantity(100);
        itemForm.setAuthor("author");
        itemForm.setIsbn("isbn0001");
        itemRepository.save(Book.createBook(itemForm));

        ItemForm itemForm2 = new ItemForm();
        itemForm2.setName("testbook2");
        itemForm2.setPrice(20000);
        itemForm2.setStockQuantity(200);
        itemForm2.setAuthor("author2");
        itemForm2.setIsbn("isbn0002");
        itemRepository.save(Book.createBook(itemForm2));

        Member findMember = memberRepository.findByUsername("user0");
        Optional<Book> findItem = itemRepository.findBookByItemName("testbook1");
        ordersService.ordersCreate(findMember.getId(), findItem.get().getId(), 10);

        entityManager.flush();
        entityManager.clear();

        Member findMember2 = memberRepository.findByUsername("user0");
        Optional<Book> findItem2 = itemRepository.findBookByItemName("testbook2");
        ordersService.ordersCreate(findMember2.getId(), findItem2.get().getId(), 20);

        entityManager.flush();
        entityManager.clear();

    }

    @Test
    void searchOrdersByConditionTest() {

        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setMemberName("user0");
        condition.setOrderStatus(OrderStatus.COMPLETE);

        List<OrdersDto> ordersDto = ordersRepository.searchByCondition(condition);
        for (OrdersDto dto : ordersDto) {
            System.out.println("dto = " + dto.getName());
            assertThat(dto.getName()).isEqualTo("user0");
        }
    }

    @Test
    void searchOrdersTest() {

        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setMemberName("user0");
        condition.setOrderStatus(OrderStatus.COMPLETE);

        QueryResults<Tuple> orders = ordersRepository.searchOrders(condition);
        List<Tuple> results = orders.getResults();
        Tuple order1 = results.get(0);
        System.out.println("order1 = " + order1.get(QOrders.orders.member.username));
        assertThat(order1.get(QOrders.orders.member.username)).isEqualTo("user0");
    }

    @Test
    void searchOrdersItem() {
        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setMemberName("user0");
        condition.setOrderStatus(OrderStatus.COMPLETE);

        QueryResults<Tuple> orders = ordersRepository.searchOrders(condition);
        List<Tuple> results = orders.getResults();
        Tuple order1 = results.get(0);

        List<OrdersItem> ordersItems = ordersRepository.searchOrdersItem(order1.get(QOrders.orders.id));

        for (OrdersItem ordersItem : ordersItems) {
            System.out.println("ordersItem = " + ordersItem.getItem().getItemName());
        }

        assertThat(ordersItems).extracting(ordersItem -> ordersItem.getItem().getItemName()).containsExactly("testbook1");
    }

    @Test
    void searchOrderItemDtoTest() {
        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setMemberName("user0");
        condition.setOrderStatus(OrderStatus.COMPLETE);

        List<OrdersDto> ordersDtos = ordersRepository.searchByCondition(condition);

        List<Long> ordersIds = ordersDtos
                .stream()
                .map(object -> object.getId())
                .collect(Collectors.toList());

        List<OrdersItemDto> ordersitems = ordersRepository.searchOrdersItemDto(ordersIds);

        for (OrdersItemDto ordersitem : ordersitems) {
            System.out.println("ordersitem = " + ordersitem);
        }

        Map<Long, List<OrdersItemDto>> ordersItemMap = ordersitems
                .stream()
                .collect(
                        Collectors.groupingBy(ordersItemDto -> ordersItemDto.getOrderId()
                        )
                );

        ordersDtos.forEach(o->o.setOrderItems(ordersItemMap.get(o.getId())));

        int idx = 1;
        for(OrdersDto o :  ordersDtos){
            assertThat(o.getOrderItems()).extracting("itemName").containsExactly("testbook"+idx);
            idx++;
        }
    }

    @Test
    void nPlusOneTest() {
        System.out.println("------------------------nPlusOneTest start------------------------------");
        List<Orders> all = ordersRepository.findAll();

        for (Orders orders : all) {
            String username = orders.getMember().getUsername();
            System.out.println("username = " + username);
            for (OrdersItem ordersItem : orders.getOrdersItemList()) {
                String item = ordersItem.getItem().getItemName();
                System.out.println("item = " + item);
            }

        }
    }
}