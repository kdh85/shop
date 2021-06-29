package com.exam.shop.service;

import com.exam.shop.domain.dto.ItemDto;
import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.entity.itemtype.Book;
import com.exam.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void itemCreate(ItemForm itemForm) {
        Book newBook = Book.createBook(itemForm);
        itemRepository.save(newBook);
    }

    public List<ItemDto> getItemsByForm(ItemForm itemForm) {
        return itemRepository.findByItemNameList(itemForm);
    }

    public ItemForm getItemById(Long itemId) {
        Optional<Book> book = itemRepository.findBookById(itemId);

        ItemForm itemForm = new ItemForm();
        if(book.isPresent()){
            itemForm.setName(book.get().getItemName());
            itemForm.setPrice(book.get().getPrice());
            itemForm.setStockQuantity(book.get().getQuantity());
            itemForm.setAuthor(book.get().getAuthor());
            itemForm.setIsbn(book.get().getIsbn());
        }

        return itemForm;
    }

    @Transactional
    public void itemUpdate(Long itemId, ItemForm itemForm) {

        Book.updateBook(itemRepository.findBookById(itemId), itemForm);

    }
}
