package com.example.projekt.services;

import com.example.projekt.data.Category;
import com.example.projekt.data.Information;
import com.example.projekt.repositories.CategoryRepository;
import com.example.projekt.repositories.InformationRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
public class InformationService {

    @Autowired
    private InformationRepository informationRepository;


    @Autowired
    private CategoryRepository categoryRepository;

    public List<Information> getAllInformations() {
        return informationRepository.getInformations();
    }

    public Information createInformation(String noteName, String categoryName, String descriptionNote) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);

        // Check if the category already exists in the repository
        List<Category> categories = categoryRepository.getCategories();
        Category category = categories.stream()
                .filter(c -> c.getName().equals(categoryName))
                .findFirst()
                .orElse(null);

        // If the category does not exist, create a new one
        if (category == null) {
            category = new Category(categoryName);
            categories.add(category);
        }

        // Create the new information object and add it to the information repository
        Information information = new Information(Integer.toString(informationRepository.getInformations().size() + 1), noteName, categoryName, category, formattedDateTime);
        informationRepository.getInformations().add(information);
        return information;
    }

    public List<Information> getInformationsByCategoryName(String categoryName) {
        List<Information> informations = new ArrayList<>();
        for (Information information : getAllInformations()) {
            if (information.getCategory().getName().equals(categoryName)) {
                informations.add(information);
            }
        }
        return informations;
    }

    public Category createCategory(String categoryName) {
        Category category = new Category(categoryName);
        categoryRepository.getCategories().add(category);
        return category;
    }

    public List<Category> getCategoriesByKeyword(String keyword) {
        return categoryRepository.getCategories().stream()
                .filter(category -> category.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

}
