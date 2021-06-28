package com.exam.shop.controller;

import com.exam.shop.domain.dto.MemberDto;
import com.exam.shop.domain.dto.MemberForm;
import com.exam.shop.domain.search.MemberSearchCondition;
import com.exam.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String memberCreateView(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String memberCreate(@Valid MemberForm memberForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "members/createMemberForm";
        }

        memberService.join(memberForm);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model, MemberSearchCondition condition,@PageableDefault(size = 20) Pageable pageable){
        Page<MemberDto> memberByCondition = memberService.findMemberByCondition(condition, pageable);
        model.addAttribute("members", memberByCondition);
        model.addAttribute("pageable",pageable);
        model.addAttribute("total",memberByCondition.getTotalPages());
        model.addAttribute("hasNext",memberByCondition.hasNext());
        return "members/memberList";
    }
}
