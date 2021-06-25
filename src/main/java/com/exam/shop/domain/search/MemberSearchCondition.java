package com.exam.shop.domain.search;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MemberSearchCondition {

    private String username;

    private String city;

    private String street;

    private String zipcode;
}
