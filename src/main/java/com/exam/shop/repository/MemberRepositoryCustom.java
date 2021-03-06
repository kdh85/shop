package com.exam.shop.repository;

import com.exam.shop.domain.dto.MemberDto;
import com.exam.shop.domain.search.MemberSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {
    Page<MemberDto> findMemberByCondition(MemberSearchCondition condition, Pageable pageable);

    List<MemberDto> findAllMembers();
}
