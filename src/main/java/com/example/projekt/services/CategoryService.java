package com.example.projekt.services;

import com.example.projekt.data.Category;
import com.example.projekt.repositories.CategoryRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        categoryRepository.save(new Category("Zarządzanie projektami"));
        categoryRepository.save(new Category("Marketing"));
        categoryRepository.save(new Category("Finanse"));
        categoryRepository.save(new Category("Zarządzanie IT"));
        categoryRepository.save(new Category("Nauka"));
    }

    public Category getOrCreateCategory(String categoryName) {
        Category category = categoryRepository.getCategoryByName(categoryName);
        if (category == null) {
            category = new Category(categoryName);
            categoryRepository.save(category);
        }
        return category;
    }
}