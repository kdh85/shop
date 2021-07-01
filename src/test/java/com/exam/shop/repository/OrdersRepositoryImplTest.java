package com.exam.shop.repository;

import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.dto.OrderSearchCondition;
import com.exam.shop.domain.dto.OrdersDto;
import com.exam.shop.domain.dto.OrdersItemDto;
import com.exam.shop.domain.entity.Member;
import com.exam.shop.domain.entity.OrderStatus;
import com.exam.shop.domain.entity.OrdersItem;
import com.exam.shop.domain.entity.QOrders;
import com.exam.shop.domain.entity.itemtype.Book;
import com.exam.shop.service.OrdersService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
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
        itemForm.setName("book1");
        itemForm.setPrice(10000);
        itemForm.setStockQuantity(100);
        itemForm.setAuthor("author");
        itemForm.setIsbn("isbn0001");
        itemRepository.save(Book.createBook(itemForm));

        searchOrdersTest();
    }

    @Test
    void searchOrdersByConditionTest() {
        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setMemberName("user0");
        condition.setOrderStatus(OrderStatus.COMPLETE);

        List<OrdersDto> ordersDto = ordersRepository.searchByCondition(condition);
        for (OrdersDto dto : ordersDto) {
            System.out.println("dto = " + dto.getName());
        }
    }

    @Test
    void searchOrdersTest() {

        Member findMember = memberRepository.findByUsername("user0");
        Optional<Book> findItem = itemRepository.findBookByItemName("book1");

        Long orderId = ordersService.ordersCreate(findMember.getId(), findItem.get().getId(), 10);
        System.out.println("orderId = " + orderId);

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
    @Commit
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

        assertThat(ordersItems).extracting(ordersItem -> ordersItem.getItem().getItemName()).containsExactly("book1");
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
        System.out.println(" ============================================================ ");
        for(OrdersDto o :  ordersDtos){
            System.out.println("o = " + o);
            assertThat(o.getOrderItems()).extracting("itemName").containsExactly("book1");
        }
    }
}