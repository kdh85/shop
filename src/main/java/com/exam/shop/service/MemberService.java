package com.exam.shop.service;

import com.exam.shop.domain.dto.MemberDto;
import com.exam.shop.domain.dto.MemberForm;
import com.exam.shop.domain.entity.Address;
import com.exam.shop.domain.entity.Member;
import com.exam.shop.domain.search.MemberSearchCondition;
import com.exam.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void join(MemberForm memberForm) {

        memberRepository.save(
                Member.makeMemberWithAddress(
                        memberForm.getName(),
                        memberForm.getAge(),
                        new Address(
                                memberForm.getCity(),
                                memberForm.getStreet(),
                                memberForm.getZipcode()
                        ))
        );
    }

    public Page<MemberDto> findMemberByCondition(MemberSearchCondition condition, Pageable pageable) {
        //pageable = PageRequest.of((int) pageable.getOffset(), pageable.getPageSize(), Sort.by("id").ascending());
        return memberRepository.findMemberByCondition(condition, pageable);
    }
}
