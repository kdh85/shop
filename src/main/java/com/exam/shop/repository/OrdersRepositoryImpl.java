package com.exam.shop.repository;

import com.exam.shop.domain.dto.OrderSearchCondition;
import com.exam.shop.domain.dto.OrdersDto;
import com.exam.shop.domain.dto.QOrdersDto;
import com.exam.shop.domain.entity.OrdersItem;
import com.exam.shop.domain.entity.QMember;
import com.exam.shop.domain.entity.QOrdersItem;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.exam.shop.domain.entity.QOrders.orders;

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
                                orders.member.id,
                                orders.ordersItemList,
                                orders.orderStatus,
                                orders.orderDateTime
                        ))
                .from(orders)
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
                .join(orders.member, QMember.member)
                .where(
                        usernameEq(condition.getMemberName())
                )
                .fetchResults();
    }

    public List<OrdersItem> searchOrdersItem(Long orderId) {
        return queryFactory
                .selectFrom(QOrdersItem.ordersItem)
                .where(
                        QOrdersItem.ordersItem.orders.id.eq(orderId)
                )
                .fetch();
    }

    private BooleanExpression usernameEq(String username) {
        return StringUtils.hasText(username) ? orders.member.username.eq(username) : null;
    }
}
