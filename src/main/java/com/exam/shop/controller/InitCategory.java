package com.exam.shop.controller;

import com.exam.shop.domain.entity.Category;
import com.exam.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile(value = "local")
@Component
@RequiredArgsConstructor
public class InitCategory {

    private final InitCategoryService initCategoryService;

    @PostConstruct
    public void init(){
        initCategoryService.createCategoryInfoByRepo();
    }

    @Component
    static class InitCategoryService {

        @Autowired
        CategoryRepository categoryRepository;

        public void createCategoryInfoByRepo(){
            Category product = Category.createParent("제품");
            categoryRepository.save(product);
            Category book = Category.createChildren("책", product);
            categoryRepository.save(book);
        }
    }
}
