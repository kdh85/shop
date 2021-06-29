package com.exam.shop.repository;

import com.exam.shop.domain.dto.ItemDto;
import com.exam.shop.domain.dto.ItemForm;

import java.util.List;

public interface ItemRepositoryCustom {
    List<ItemDto> findByItemNameList(ItemForm searchItemFrom);
}
