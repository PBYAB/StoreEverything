package com.example.projekt.repositories;

import com.example.projekt.data.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
@Setter
@AllArgsConstructor
public class CategoryRepository {
    private List<Category> categories = new ArrayList<>();

    public CategoryRepository() {
        Category category1 = new Category("Praca");
        Category category2 = new Category("Zakupy");
        Category category3 = new Category("Dom");
        Category category4 = new Category("Rozrywka");
        Category category5 = new Category("Inne");

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);
    }


    public Category getCategoryByName(String name) {
        return categories.stream()
                .filter(category -> category.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
