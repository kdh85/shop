package com.exam.shop.controller;

import com.exam.shop.domain.dto.CategoryDto;
import com.exam.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/category/new")
    public String createCategoryView(Model model) {
        model.addAttribute("categoryForm",new CategoryDto());
        model.addAttribute("parentCategory",categoryService.getCategoryInfo());
        return "category/createCategoryForm";
    }

    @PostMapping(value = "/category/new")
    public String createCategory(CategoryDto categoryDto){
        categoryService.createCategory(categoryDto);
        return "redirect:/";
    }


}
