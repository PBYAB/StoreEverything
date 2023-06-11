package com.example.projekt.repositories;


import com.example.projekt.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findByName(String name);

    Category findByNameAndLogin(String name,String login);

    List<Category> findAllByLogin(String login);
}
