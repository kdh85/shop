package com.exam.shop.domain.entity.itemtype;

import com.exam.shop.domain.dto.ItemForm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class BookTest {

    @Test
    void createBookWithCategoryTest() {
        ItemForm itemForm = new ItemForm();
        itemForm.setName("testbook");
        itemForm.setPrice(1000);
        itemForm.setStockQuantity(10);
        itemForm.setAuthor("tester");
        itemForm.setIsbn("123123");
        itemForm.setCategoryId(1L);

        Book bookWithCategory = Book.createBookWithCategory(itemForm);

        assertThat(bookWithCategory.getCategoryId()).isEqualTo(1L);
    }

    @Test
    void updateBookWithCategory() {

        ItemForm itemForm = new ItemForm();
        itemForm.setName("testbook");
        itemForm.setPrice(1000);
        itemForm.setStockQuantity(10);
        itemForm.setAuthor("tester");
        itemForm.setIsbn("123123");
        itemForm.setCategoryId(1L);

        Book bookWithCategory = Book.createBookWithCategory(itemForm);
        itemForm.setCategoryId(2l);
        Book.updateBookWithCategory(java.util.Optional.of(bookWithCategory),itemForm);

        assertThat(bookWithCategory.getCategoryId()).isEqualTo(2L);
    }
}