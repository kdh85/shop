package com.exam.shop.controller;

import com.exam.shop.domain.dto.ItemForm;
import com.exam.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/items/new")
    public String itemCreateView(Model model){
        model.addAttribute("itemForm",new ItemForm());
        return "items/createItemForm";
    }

    @PostMapping(value = "/items/new")
    public String itemCreate(@Valid ItemForm itemForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "items/createItemForm";
        }

        itemService.itemCreate(itemForm);
        return "redirect:/";
    }

    @GetMapping(value = "/items")
    public String itemsView(Model model, ItemForm itemForm){
        model.addAttribute("items",itemService.getItemsByForm(itemForm));
        return "items/itemList";
    }

    @GetMapping(value = "/items/{itemId}/edit")
    public String itemUpdateView(Model model, @PathVariable("itemId") Long itemId){
        model.addAttribute("form",itemService.getItemById(itemId));

        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String itemUpdate(@PathVariable("itemId") Long itemId, @Valid ItemForm itemForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("form",itemForm);
            return "items/updateItemForm";
        }

        itemService.itemUpdate(itemId, itemForm);

        return "redirect:/";
    }
}
