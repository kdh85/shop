package com.exam.shop.repository;

import com.exam.shop.domain.dto.*;
import com.exam.shop.domain.entity.OrderStatus;
import com.exam.shop.domain.entity.OrdersItem;
import com.exam.shop.domain.search.OrderSearchCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.exam.shop.domain.entity.QItem.item;
import static com.exam.shop.domain.entity.QMember.member;
import static com.exam.shop.domain.entity.QOrders.orders;
import static com.exam.shop.domain.entity.QOrdersItem.ordersItem;

public class OrdersRepositoryImpl implements OrdersRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrdersRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public List<OrdersDto> searchByCondition(OrderSearchCondition condition) {

        List<OrdersDto> ordersDtos = queryFactory
                .select(
                        new QOrdersDto(
                                orders.id,
                                orders.member.username,
                                orders.orderStatus,
                                orders.orderDateTime
                        ))
                .from(orders)
                .join(orders.member, member)
                .where(
                        usernameEq(condition.getMemberName()),
                        orderStatusEq(condition.getOrderStatus()),
                        orderIdEq(condition.getOrderId())
                )
                .fetch();

        return ordersDtos;
    }

    public QueryResults<Tuple> searchOrders(OrderSearchCondition condition) {
        return queryFactory
                .select(
                        orders.id,
                        orders.member.username,
                        orders.orderStatus,
                        orders.orderDateTime
                )
                .from(orders)
                .join(orders.member, member)
                .where(
                        usernameEq(condition.getMemberName())
                )
                .fetchResults();
    }

    public List<OrdersItem> searchOrdersItem(Long orderId) {
        return queryFactory
                .selectFrom(ordersItem)
                .where(
                        ordersItem.orders.id.eq(orderId)
                )
                .fetch();
    }

    @Override
    public List<OrdersItemDto> searchOrdersItemDto(List<Long> ordersId) {
        return queryFactory
                .select(
                        new QOrdersItemDto(
                                ordersItem.orders.id,
                                ordersItem.item.itemName,
                                ordersItem.orderPrice,
                                ordersItem.orderQuantity
                        )
                )
                .from(ordersItem)
                .join(ordersItem.item, item)
                .where(ordersItem.orders.id.in(ordersId))
                .fetch();
    }

    public List<OrdersDto> searchOrdersWithItems(OrderSearchCondition condition){

        /*
            1:N????????? ???????????? ???????????? ????????? ????????? ?????? ???????????? ???????????? ???????????? ????????? ??????.

            1.?????? ????????? ????????? ??????.(??????,???????????? ?????????. inner join)
            2.??????????????? ???????????? ????????? ???????????? ??????.
            3.????????? ???????????? Map??? ?????????. ?????? ?????? ????????? ????????????(fk)????????? ??????.
            4.???????????? DTO??? ????????? ????????? ??? ????????????????????? ??????????????? ???????????? ????????? ????????????.
         */
        List<OrdersDto> ordersDtos = searchByCondition(condition);

        Map<Long, List<OrdersItemDto>> ordersItemMap = getOrderItemsMap(getOrdersIds(ordersDtos));

        ordersDtos.forEach(o->o.setOrderItems(ordersItemMap.get(o.getId())));

        return ordersDtos;
    }

    private Map<Long, List<OrdersItemDto>> getOrderItemsMap(List<Long> ordersId) {

        Map<Long, List<OrdersItemDto>> ordersItemMap = searchOrdersItemDto(ordersId)
                .stream()
                .collect(
                        Collectors.groupingBy(ordersItemDto -> ordersItemDto.getOrderId()
                        )
                );
        return ordersItemMap;
    }

    private List<Long> getOrdersIds(List<OrdersDto> ordersDtos) {
        List<Long> ordersIds = ordersDtos
                .stream()
                .map(object -> object.getId())
                .collect(Collectors.toList());
        return ordersIds;
    }

    private BooleanExpression usernameEq(String username) {
        return StringUtils.hasText(username) ? orders.member.username.eq(username) : null;
    }

    private BooleanExpression orderStatusEq(OrderStatus orderStatus) {
        return orderStatus != null ? orders.orderStatus.eq(orderStatus) : null;
    }

    private BooleanExpression orderIdEq(Long orderId) {
        return orderId != null ? orders.id.eq(orderId) : null;
    }
}
