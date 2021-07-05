package com.exam.shop.repository;

import com.exam.shop.domain.dto.ItemDto;
import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.entity.itemtype.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemSaveTest();
    }

    @Test
    void itemSaveTest() {
        ItemForm newItem = new ItemForm();
        newItem.setName("testbook1");
        newItem.setPrice(10000);
        newItem.setStockQuantity(10);
        newItem.setAuthor("tester");
        newItem.setIsbn("test123");

        itemRepository.save(Book.createBook(newItem));

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void getItemListInfo() {
        ItemForm searchItem = new ItemForm();
        searchItem.setName("testbook1");
        List<ItemDto> byItemName = itemRepository.findByItemNameList(searchItem);

        assertThat(byItemName).extracting("name").containsExactly(searchItem.getName());
    }

    @Test
    void updateItemInfo() {

        ItemForm searchItem = new ItemForm();
        searchItem.setName("testbook1");

        Optional<Book> bookByName = itemRepository.findBookByItemName(searchItem.getName());
        //Optional<Book> bookByName = itemRepository.findBookById(1L);

        if(bookByName.isPresent()){
            bookByName.get().setAuthor("111");
            bookByName.get().setItemName("newTestbook1");
            //entityManager.flush();
            //entityManager.clear();
        }

        assertThat(bookByName.get().getAuthor()).isEqualTo("111");
        assertThat(bookByName.get().getItemName()).isEqualTo("newTestbook1");
    }


    @Test
    void discriminateTest() {
        List<Book> dtype = itemRepository.findDType();
        for (Book book : dtype) {
            System.out.println("book = " + book.getItemName());
            System.out.println("book = " + book.getDType());
        }
    }
}