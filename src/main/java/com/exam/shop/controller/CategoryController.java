package com.exam.shop.controller;

import com.exam.shop.domain.dto.CategoryAllDto;
import com.exam.shop.domain.dto.CategoryDto;
import com.exam.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

    @GetMapping(value = "/categories")
    public String categoryView(Model model){

        List<CategoryAllDto> categoriesInfo = categoryService.getCategoriesInfo();
        for (CategoryAllDto categoryAllDto : categoriesInfo) {
            System.out.println("categoryAllDto = " + categoryAllDto);
        }
        model.addAttribute("categories", categoriesInfo);
        return "category/categoryList";
    }

}
