package com.exam.shop.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery")
    private Orders orders;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    public DeliveryStatus deliveryStatus;

    public static Delivery makeDelivery(Address address,DeliveryStatus status){
        Delivery newDelivery = new Delivery();
        newDelivery.setAddress(address);
        newDelivery.setDeliveryStatus(status);

        return newDelivery;
    }
}
