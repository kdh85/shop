package com.exam.shop.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;

    private int age;

    private String city;

    private String street;

    private String zipcode;

}
