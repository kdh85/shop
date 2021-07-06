package com.exam.shop.service;

import com.exam.shop.domain.search.OrderSearchCondition;
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
        return ordersRepository.save(getOrders(memberId, itemId, count)).getId();
    }

    private Orders getOrders(Long memberId, Long itemId, int count) {

        Orders order = Orders.createOrderWithItems(
                Optional.ofNullable(memberRepository.findById(memberId).get()).get(),
                OrderStatus.COMPLETE,
                getOrdersItem(itemId, count));

        return order;
    }

    private OrdersItem getOrdersItem(Long itemId, int count) {
        OrdersItem ordersItem = OrdersItem.makeOrderItem(
                Optional.ofNullable(itemRepository.findById(itemId).get()).get(),
                Optional.ofNullable(itemRepository.findById(itemId).get().getPrice()).get(),
                count);
        return ordersItem;
    }

    public List<OrdersDto> searchOrdersWithItems(OrderSearchCondition condition){
        return ordersRepository.searchOrdersWithItems(condition);
    }

    @Transactional
    public void ordersCancel(Long ordersId) {
        Optional.ofNullable(ordersRepository.findById(ordersId).get()).get().cancelOrder();
    }
}
