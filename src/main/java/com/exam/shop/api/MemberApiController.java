package com.exam.shop.api;

import com.exam.shop.domain.dto.MemberDto;
import com.exam.shop.domain.search.MemberSearchCondition;
import com.exam.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v2/members")
    public Page<MemberDto> membersV2(Model model, MemberSearchCondition condition, @PageableDefault(size = 20) Pageable pageable){

        return memberService.findMemberByCondition(condition, pageable);
    }
}
