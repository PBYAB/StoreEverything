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
       /* categoryRepository.save(new Category("zarządzanie"));
        categoryRepository.save(new Category("marketing"));
        categoryRepository.save(new Category("finanse"));
        categoryRepository.save(new Category("zarządzanie it"));
        categoryRepository.save(new Category("nauka"));*/
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