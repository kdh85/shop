package com.exam.shop.service;

import com.exam.shop.domain.dto.CategoryAllDto;
import com.exam.shop.domain.dto.CategoryDto;
import com.exam.shop.domain.dto.CategoryForm;
import com.exam.shop.domain.entity.Category;
import com.exam.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void createCategory(CategoryForm categoryForm){

        CategoryDto categoryDto = createCategoryDto(categoryForm);

        if(categoryDto.getParentId() != null){
            Optional<Category> findParent = categoryRepository.findById(categoryDto.getParentId());
            createChildCategory(categoryDto, findParent.get());
        }else{
            createParentCategory(categoryDto);
        }

    }

    private CategoryDto createCategoryDto(CategoryForm categoryForm) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName(categoryForm.getCategoryName());
        categoryDto.setParentId(categoryForm.getParentId());
        return categoryDto;
    }

    private void createChildCategory(CategoryDto categoryDto, Category findParent) {
        Category child = Category.createChildren(categoryDto.getCategoryName(), findParent);
        categoryRepository.save(child);
    }

    private void createParentCategory(CategoryDto categoryDto) {
        Category findParent;
        findParent = Category.createParent(categoryDto.getCategoryName());
        categoryRepository.save(findParent);
    }

    public List<CategoryDto> getCategoryInfo(){
        return categoryRepository.findByTopByDto();
    }

    public List<CategoryAllDto> getCategoriesInfo() {
        return categoryRepository.findByCategories();
    }
}
