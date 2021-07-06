package com.exam.shop.controller;

import com.exam.shop.domain.dto.CategoryForm;
import com.exam.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/category/new")
    public String createCategoryView(Model model) {
        model.addAttribute("categoryForm",new CategoryForm());
        model.addAttribute("parentCategory",categoryService.getCategoryInfo());
        return "category/createCategoryForm";
    }

    @PostMapping(value = "/category/new")
    public String createCategory(@Valid CategoryForm categoryForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "category/createCategoryForm";
        }

        categoryService.createCategory(categoryForm);
        return "redirect:/";
    }

    @GetMapping(value = "/categories")
    public String categoryView(Model model){
        model.addAttribute("categories", categoryService.getCategoriesInfo());
        return "category/categoryList";
    }

}
