package com.example.projekt.repositories;


import com.example.projekt.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category getCategoryByName(String name);
}
