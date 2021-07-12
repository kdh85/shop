package com.exam.shop.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userEmail) {
        super(userEmail+ ">>>>>>>>>>>>>>>>>>>Not found");
    }
}
