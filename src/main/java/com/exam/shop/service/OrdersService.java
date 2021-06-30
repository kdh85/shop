package com.exam.shop.service;

import com.exam.shop.domain.dto.OrderSearchCondition;
import com.exam.shop.domain.dto.OrdersDto;
import com.exam.shop.domain.entity.*;
import com.exam.shop.repository.ItemRepository;
import com.exam.shop.repository.MemberRepository;
import com.exam.shop.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersService {

    private final OrdersRepository ordersRepository;

    private final MemberRepository memberRepository;

    private final ItemRepository itemRepository;

    @Transactional
    public Long ordersCreate(Long memberId, Long itemId, int count) {

        Orders order = getOrders(memberId, itemId, count);

        ordersRepository.save(order);

        return order.getId();
    }

    private Orders getOrders(Long memberId, Long itemId, int count) {

        Optional<Member> findMember = memberRepository.findById(memberId);

        OrdersItem ordersItem = getOrdersItem(itemId, count);

        Orders order = Orders.createOrderWithItems(
                Optional.ofNullable(findMember.get()).get(),
                OrderStatus.COMPLETE,
                ordersItem);

        return order;
    }

    private OrdersItem getOrdersItem(Long itemId, int count) {
        Optional<Item> findItem = itemRepository.findById(itemId);

        OrdersItem ordersItem = OrdersItem.makeOrderItem(
                Optional.ofNullable(findItem.get()).get(),
                Optional.ofNullable(findItem.get().getPrice()).get(),
                count);
        return ordersItem;
    }

    public List<OrdersDto> searchByCondition(OrderSearchCondition condition){
        return ordersRepository.searchByCondition(condition);
    }
}
