package com.example.projekt.services;

import com.example.projekt.repositories.CategoryRepositoryInterface;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class CategoryServiceWithJpa {
    @Autowired
    CategoryRepositoryInterface categoryRepositoryInterface;

    public CategoryServiceWithJpa(CategoryRepositoryInterface categoryRepositoryInterface) {
        this.categoryRepositoryInterface = categoryRepositoryInterface;
    }
}