package com.exam.shop.domain.entity.itemtype;

import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.domain.entity.Category;
import com.exam.shop.domain.entity.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {

    @Id
    @GeneratedValue
    private Long id;
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

        Category category = getCategory(itemForm);

        newBook.setCategory(category);

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

            Category category = getCategory(itemForm);
            updateBook.setCategory(category);

            return updateBook;
        }
        return new Book();
    }

    private static Category getCategory(ItemForm itemForm) {
        Category category = new Category();
        category.setId(itemForm.getCategoryId());
        return category;
    }
}
