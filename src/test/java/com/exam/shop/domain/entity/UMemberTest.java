package com.exam.shop.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class UMemberTest {

    @Autowired
    EntityManager entityManager;

    @Test
    @Commit
    void UUIDecorateTest() {
        UMember uMember = new UMember();
        uMember.setName("uMember");
        entityManager.persist(uMember);
    }
}