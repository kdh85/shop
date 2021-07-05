package com.exam.shop.repository;

import com.exam.shop.domain.dto.ItemDto;
import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.entity.itemtype.Book;

import java.util.List;

public interface ItemRepositoryCustom {
    List<ItemDto> findByItemNameList(ItemForm searchItemFrom);
    List<ItemDto> findAllByBook();
    List<Book> findDType(String dType);
}
