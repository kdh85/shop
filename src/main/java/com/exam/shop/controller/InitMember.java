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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Profile(value = "local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;

    @PostConstruct
    public void init(){
        //initMemberService.createMemberInfo();
        //initMemberService.createMemberInfoByRepo();
        initMemberService.createBulkMemberInfoByRepo();
    }

    @Component
    static class InitMemberService {

        @PersistenceContext
        private EntityManager entityManager;

        @Autowired
        private MemberRepository memberRepository;

        @Transactional
        public void createMemberInfo(){
            Random rd = new Random();

            for (int i=1; i<101; i++){
                int zipcode = rd.nextInt(1000000-1);
                String zipcodeFormat = String.format("%06d", zipcode);
                Member member = Member.makeMemberWithAddress("user" + i, i, new Address("서울", String.valueOf(i * 10), zipcodeFormat));
                entityManager.persist(member);
            }
        }

        //건단위 insert.
        public void createMemberInfoByRepo(){
            Random rd = new Random();

            for (int i=1; i<101; i++){
                int zipcode = rd.nextInt(1000000-1);
                String zipcodeFormat = String.format("%06d", zipcode);
                Member member = Member.makeMemberWithAddress("user" + i, i, new Address("서울", String.valueOf(i * 10), zipcodeFormat));
                memberRepository.save(member);
            }
        }

        //batch_size 단위로 bulk insert.
        public void createBulkMemberInfoByRepo(){
            List<Member> members = new ArrayList<>();
            Random rd = new Random();

            for (int i=1; i<101; i++){
                int zipcode = rd.nextInt(1000000-1);
                String zipcodeFormat = String.format("%06d", zipcode);
                Member member = Member.makeMemberWithAddress("user" + i, i, new Address("서울", String.valueOf(i * 10), zipcodeFormat));
                members.add(member);
            }
            memberRepository.saveAll(members);
        }
    }
}
