package com.exam.shop.controller;

import com.exam.shop.domain.entity.Address;
import com.exam.shop.domain.entity.Member;
import com.exam.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Profile(value = "local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;

    @PostConstruct
    public void init(){
        //initMemberService.createMemberInfo();
        initMemberService.createMemberInfoByRepo();
    }

    @Component
    static class InitMemberService {

        @PersistenceContext
        private EntityManager entityManager;

        @Autowired
        private MemberRepository memberRepository;

        @Transactional
        public void createMemberInfo(){
            for (int i=1; i<101; i++){
                Member member = Member.makeMemberWithAddress("user" + i, i, new Address("서울", String.valueOf(i * 10), String.valueOf(Math.random() * 100000)));
                entityManager.persist(member);
            }
        }

        public void createMemberInfoByRepo(){
            for (int i=1; i<101; i++){
                Member member = Member.makeMemberWithAddress("user" + i, i, new Address("서울", String.valueOf(i * 10), String.valueOf(Math.random() * 100000)));
                memberRepository.save(member);
            }
        }
    }
}
