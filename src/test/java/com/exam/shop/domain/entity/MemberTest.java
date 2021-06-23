package com.exam.shop.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MemberTest {

    @Test
    void makeMember() {
        Member makeMember = Member.makeMember("username",10);
        System.out.println("makeMember = " + makeMember);
        assertThat(makeMember.getUsername()).isEqualTo("username");

        Address home = new Address("city","street","11111");
        makeMember.setAddress(home);
        assertThat(makeMember.getAddress()).isEqualTo(home);
    }

    @Test
    void makeMemberWithAddress() {
        Member makeMember = Member.makeMemberWithAddress("username",10,new Address("city","street","11111"));
        assertThat(makeMember.getAddress().getCity()).isEqualTo("city");
    }
}