package com.exam.shop.service;

import com.exam.shop.domain.dto.ItemDto;
import com.exam.shop.domain.dto.ItemForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemSaveTest();
    }

    @Test
    void itemSaveTest() {
        ItemForm itemForm = new ItemForm();
        itemForm.setName("book1");
        itemForm.setPrice(10000);
        itemForm.setStockQuantity(100);
        itemForm.setAuthor("author");
        itemForm.setIsbn("isbn0001");

        itemService.itemCreate(itemForm);

        ItemForm findOne = itemService.getItemById(1l);
        assertThat(findOne.getName()).isEqualTo("book1");
    }

    @Test
    void itemUpdateTest() {
        ItemForm findItem = itemService.getItemById(1L);
        findItem.setName("newOne");
        findItem.setPrice(200);
        findItem.setStockQuantity(50);
        findItem.setAuthor("aaaa");
        findItem.setIsbn("isbn0002");

        assertThat(findItem.getName()).isEqualTo("newOne");
        assertThat(findItem.getPrice()).isEqualTo(200);
        assertThat(findItem.getStockQuantity()).isEqualTo(50);
        assertThat(findItem.getAuthor()).isEqualTo("aaaa");
        assertThat(findItem.getIsbn()).isEqualTo("isbn0002");
    }

    @Test
    void findAllItemsTest() {
        List<ItemDto> allItems = itemService.findAllItems();
        assertThat(allItems.size()).isEqualTo(1);
    }
}