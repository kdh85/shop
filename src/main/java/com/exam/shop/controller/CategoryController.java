package com.exam.shop.controller;

import com.exam.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/category/new")
    public String createCategoryView(Model model) {
        model.addAttribute("categoryForm","");
        return "category/createCategoryForm";
    }
}
