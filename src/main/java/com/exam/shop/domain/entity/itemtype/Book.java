package com.exam.shop.domain.entity.itemtype;

import com.exam.shop.domain.entity.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Book extends Item {

    @Id
    @GeneratedValue
    private Long id;
    private String author;
    private String isbn;
}
