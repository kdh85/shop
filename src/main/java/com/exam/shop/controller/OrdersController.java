package com.exam.shop.controller;

import com.exam.shop.domain.dto.OrderSearchCondition;
import com.exam.shop.service.ItemService;
import com.exam.shop.service.MemberService;
import com.exam.shop.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    private final MemberService memberService;

    private final ItemService itemService;

    @GetMapping(value = "/order")
    public String ordersCreateView(Model model){
        model.addAttribute("members",memberService.findAllMembers());
        model.addAttribute("items",itemService.findAllItems());
        return "order/orderForm";
    }

    @PostMapping(value = "/order")
    public String ordersCreate(@RequestParam("memberId") Long memberId,
                               @RequestParam("itemId") Long itemId,
                               @RequestParam("count") int count){

        ordersService.ordersCreate(memberId, itemId, count);
        return "redirect:/";
    }

    @GetMapping(value = "/orders")
    public String ordersView(Model model, @ModelAttribute("orderSearch") OrderSearchCondition condition){

        return "order/orderList";
    }
}
