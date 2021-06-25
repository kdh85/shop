package com.exam.shop.repository;

import com.exam.shop.domain.dto.MemberDto;
import com.exam.shop.domain.dto.QMemberDto;
import com.exam.shop.domain.entity.Member;
import com.exam.shop.domain.search.MemberSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.exam.shop.domain.entity.QMember.member;

public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<MemberDto> findMemberByCondition(MemberSearchCondition condition, Pageable pageable) {

        List<MemberDto> content = queryFactory
                .select(
                        new QMemberDto(
                                member.id,
                                member.username,
                                member.address.city,
                                member.address.street,
                                member.address.zipcode
                        )
                )
                .from(member)
                .where(
                        usernameEq(condition.getUsername()),
                        cityLike(condition.getCity()),
                        streetLike(condition.getCity()),
                        zipcodeLike(condition.getCity())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Member> count = queryFactory
                .selectFrom(member)
                .where(
                        usernameEq(condition.getUsername()),
                        cityLike(condition.getCity()),
                        streetLike(condition.getCity()),
                        zipcodeLike(condition.getCity())
                );

        return PageableExecutionUtils.getPage(content,pageable,count::fetchCount);

    }

    private BooleanExpression zipcodeLike(String zipcode) {
        return StringUtils.hasText(zipcode) ? member.address.zipcode.like(zipcode) : null;
    }

    private BooleanExpression streetLike(String street) {
        return StringUtils.hasText(street) ? member.address.street.like(street) : null;
    }

    private BooleanExpression cityLike(String city) {
        return StringUtils.hasText(city) ? member.address.city.like(city) : null;
    }

    private BooleanExpression usernameEq(String username) {
        return StringUtils.hasText(username) ? member.username.eq(username) : null;
    }

}
