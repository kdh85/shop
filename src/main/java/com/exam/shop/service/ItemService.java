package com.exam.shop.service;

import com.exam.shop.domain.dto.ItemDto;
import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.entity.Item;
import com.exam.shop.domain.entity.itemtype.Book;
import com.exam.shop.repository.CategoryRepository;
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

    private final CategoryRepository categoryRepository;

    @Transactional
    public void itemCreate(ItemForm itemForm) {
        Book newBook = Book.createBook(itemForm);
        itemRepository.save(newBook);
    }

    public List<ItemDto> getItemsByForm(ItemForm itemForm) {
        return itemRepository.findByItemNameList(itemForm);
    }

    public ItemForm getItemById(Long itemId) {
        return getItemForm(itemRepository.findBookById(itemId));
    }

    private ItemForm getItemForm(Optional<Book> book) {
        Optional<Optional<Book>> book1 = Optional.ofNullable(book);
        ItemForm itemForm = new ItemForm();

        if (book.isPresent()) {
            itemForm.setName(book.get().getItemName());
            itemForm.setPrice(book.get().getPrice());
            itemForm.setStockQuantity(book.get().getQuantity());
            itemForm.setAuthor(book.get().getAuthor());
            itemForm.setIsbn(book.get().getIsbn());
            itemForm.setCategoryId(book.get().getCategoryId());
        }
        return itemForm;
    }


    @Transactional
    public void itemUpdate(Long itemId, ItemForm itemForm) {

        Book.updateBook(itemRepository.findBookById(itemId), itemForm);

    }

    public List<ItemDto> findAllItems() {
        return itemRepository.findAllByBook();
    }

    public Optional<Item> findById(long itemId) {
        return itemRepository.findById(itemId);
    }

    public Optional<Book> findByBook(String book) {
        return itemRepository.findBookByItemName(book);
    }

    @Transactional
    public void itemCreateWithCategory(ItemForm itemForm) {
        itemRepository.save(Book.createBookWithCategory(itemForm));
    }

    @Transactional
    public void itemUpdateWithCategory(Long itemId, ItemForm itemForm) {
        Book.updateBookWithCategory(itemRepository.findBookById(itemId), itemForm);
    }
}
