package com.exam.shop.domain.entity.itemtype;

import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.entity.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {

    private String author;
    private String isbn;

    public static Book createBook(ItemForm itemForm) {
        Book newBook = new Book();
        newBook.setItemName(itemForm.getName());
        newBook.setPrice(itemForm.getPrice());
        newBook.setQuantity(itemForm.getStockQuantity());
        newBook.setAuthor(itemForm.getAuthor());
        newBook.setIsbn(itemForm.getIsbn());

        return newBook;
    }

    public static Book updateBook(Optional<Book> findItem, ItemForm itemForm) {

        if(findItem.isPresent()){
            Book updateBook = findItem.get();
            updateBook.setItemName(itemForm.getName());
            updateBook.setPrice(itemForm.getPrice());
            updateBook.setQuantity(itemForm.getStockQuantity());
            updateBook.setAuthor(itemForm.getAuthor());
            updateBook.setIsbn(itemForm.getIsbn());

            return updateBook;
        }
        return new Book();
    }

    public static Book createBookWithCategory(ItemForm itemForm) {
        Book newBook = new Book();
        newBook.setItemName(itemForm.getName());
        newBook.setPrice(itemForm.getPrice());
        newBook.setQuantity(itemForm.getStockQuantity());
        newBook.setAuthor(itemForm.getAuthor());
        newBook.setIsbn(itemForm.getIsbn());
        newBook.setCategoryId(itemForm.getCategoryId());

        return newBook;
    }

    public static Book updateBookWithCategory(Optional<Book> findItem, ItemForm itemForm) {

        if(findItem.isPresent()){
            Book updateBook = findItem.get();
            updateBook.setItemName(itemForm.getName());
            updateBook.setPrice(itemForm.getPrice());
            updateBook.setQuantity(itemForm.getStockQuantity());
            updateBook.setAuthor(itemForm.getAuthor());
            updateBook.setIsbn(itemForm.getIsbn());
            updateBook.setCategoryId(itemForm.getCategoryId());

            return updateBook;
        }
        return new Book();
    }

}
