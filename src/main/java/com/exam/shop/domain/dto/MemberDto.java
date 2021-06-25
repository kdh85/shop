package com.exam.shop.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDto {

    private Long id;

    private String name;

    private String city;

    private String street;

    private String zipcode;

    @QueryProjection
    public MemberDto(Long id, String name, String city, String street, String zipcode) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
