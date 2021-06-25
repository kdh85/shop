package com.exam.shop.repository;

import com.exam.shop.domain.entity.Address;
import com.exam.shop.domain.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Commit
    void joinTest() {
        Member newMember = Member.makeMemberWithAddress("user1",10,new Address("서울","송파구","123321"));
        memberRepository.save(newMember);
        entityManager.flush();
        entityManager.clear();

        Member findMember = memberRepository.findByUsername(newMember.getUsername());
        assertThat(findMember.getUsername()).isEqualTo(newMember.getUsername());
    }
}