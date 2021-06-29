package com.exam.shop.repository;

import com.exam.shop.domain.entity.Item;
import com.exam.shop.domain.entity.itemtype.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long>, ItemRepositoryCustom {
    Optional<Book> findBookById(Long itemId);

    Optional<Book> findBookByItemName(String name);

}
