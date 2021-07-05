package com.exam.shop.domain.entity;

import com.exam.shop.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private int price;

    private int quantity;

    @Column(name = "DTYPE", insertable = false, updatable = false)
    private String dType;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "item")
    private List<OrdersItem> ordersItemList = new ArrayList<>();

    public void removeQuantity(int orderQuantity){
        int currentQuantity = this.quantity - orderQuantity;

        if(currentQuantity < 0){
            throw new NotEnoughStockException("not enought quantity");
        }

        this.quantity = currentQuantity;
    }

    public void addQuantity(int orderQuantity){
        this.quantity += orderQuantity;
    }
}