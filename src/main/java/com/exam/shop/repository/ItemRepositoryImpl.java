package com.exam.shop.repository;

import com.exam.shop.domain.dto.ItemDto;
import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.dto.QItemDto;
import com.exam.shop.domain.entity.itemtype.Book;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.exam.shop.domain.entity.itemtype.QBook.book;

public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ItemDto> findByItemNameList(ItemForm searchItemFrom) {
        List<ItemDto> results = queryFactory
                .select(new QItemDto(
                        book.id,
                        book.itemName.as("name"),
                        book.price,
                        book.quantity,
                        book.author,
                        book.isbn
                ))
                .from(book)
                .where(
                        itemNameEq(searchItemFrom.getName())
                )
                .fetch();
        return results;
    }

    @Override
    public List<ItemDto> findAllByBook() {
        List<ItemDto> results = queryFactory
                .select(new QItemDto(
                        book.id,
                        book.itemName.as("name"),
                        book.price,
                        book.quantity,
                        book.author,
                        book.isbn
                ))
                .from(book)
                .fetch();
        return results;
    }

    @Override
    public List<Book> findDType() {
        return queryFactory
                .selectFrom(book)
                .where(book.dType.eq("Book"))
                .fetch();
    }

    private BooleanExpression itemNameEq(String itemName) {
        return StringUtils.hasText(itemName) ? book.itemName.eq(itemName) : null;
    }
}
