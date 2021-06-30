package com.exam.shop.service;

import com.exam.shop.domain.dto.MemberDto;
import com.exam.shop.domain.dto.MemberForm;
import com.exam.shop.domain.search.MemberSearchCondition;
import com.exam.shop.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        joinTest();
    }

    @Test
    void joinTest() {
        MemberForm newMember = new MemberForm();
        newMember.setName("user0");
        newMember.setCity("서울");
        newMember.setStreet("송파");
        newMember.setZipcode("123321");
        memberService.join(newMember);
    }

    @Test
    void searchTest(){

        MemberForm newMember = new MemberForm();
        newMember.setName("user0");
        newMember.setCity("서울");
        newMember.setStreet("송파");
        newMember.setZipcode("123321");
        memberService.join(newMember);

        PageRequest pageRequest = PageRequest.of(0, 10);
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setUsername("user0");
        Page<MemberDto> memberByCondition = memberRepository.findMemberByCondition(condition, pageRequest);

        assertThat(memberByCondition.getContent()).extracting("name").containsExactly("user0");
    }

    @Test
    void findAllMembersTest() {
        List<MemberDto> allMembers = memberRepository.findAllMembers();

        assertThat(allMembers.size()).isEqualTo(1);
    }
}