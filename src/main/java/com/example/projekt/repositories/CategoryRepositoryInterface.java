package com.example.projekt.repositories;


import com.example.projekt.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepositoryInterface  extends JpaRepository<Category,Integer> {
}
