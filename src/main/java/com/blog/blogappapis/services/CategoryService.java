package com.blog.blogappapis.services;

import java.util.List;

import com.blog.blogappapis.payloads.CategoryDto;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    void deleteCategory(int categoryId);

    CategoryDto getCategory(int categoryId);

    List<CategoryDto> getCategories();
    
}
