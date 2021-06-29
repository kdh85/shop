package com.exam.shop.controller;

import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.entity.itemtype.Book;
import com.exam.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Profile(value = "local")
@Component
@RequiredArgsConstructor
public class InitBookItem {

    private final InitItemService initItemService;

    @PostConstruct
    public void init(){
        initItemService.createBulkBookInfoByRepo();
    }

    @Component
    static class InitItemService {

        private final ItemRepository itemRepository;

        public InitItemService(ItemRepository itemRepository) {
            this.itemRepository = itemRepository;
        }

        public void createBulkBookInfoByRepo(){

            List<Book> books = new ArrayList<>();
            Random rd = new Random();

            for(int i=0; i<20; i++){

                int quantity = rd.nextInt(1000);
                int multipleNum = rd.nextInt((10 - 1) + 1) + 1;

                ItemForm bulkBook = new ItemForm();
                bulkBook.setName("book"+i);
                bulkBook.setPrice(1000 * multipleNum);
                bulkBook.setStockQuantity(quantity);
                bulkBook.setAuthor("author"+i);
                bulkBook.setIsbn("isbn"+i);

                books.add(Book.createBook(bulkBook));
            }

            itemRepository.saveAll(books);
        }
    }
}
