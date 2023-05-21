package com.example.projekt.controllers;

import com.example.projekt.data.Category;
import com.example.projekt.data.Information;
import com.example.projekt.services.CategoryService;
import com.example.projekt.services.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/informations")
public class InformationController {

    @Autowired
    InformationService informationService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public String getInformations(@RequestParam(required = false) String categoryName,
                                  @RequestParam(required = false) String sortBy,
                                  @RequestParam(required = false) String sortDirection,
                                  Model model) {
        List<Information> informations;
        Sort.Direction direction = Sort.Direction.DESC;
        String sortField = "creationTime";

        if (sortDirection != null && sortDirection.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            if (sortBy.equalsIgnoreCase("name")) {
                sortField = "name";
            } else if (sortBy.equalsIgnoreCase("creationTime")) {
                sortField = "creationTime";
            } else if (sortBy.equalsIgnoreCase("category")) {
                sortField = "category";
            } else if (sortBy.equalsIgnoreCase("categoryOccurrences")) {
                sortField = "categoryOccurrences";
            }
        }

        if (categoryName != null && !categoryName.isEmpty()) {
            informations = informationService.getInformationRepository()
                    .getInformationByCategory(categoryName, Sort.by(direction, sortField));
        } else {
            if (sortField.equals("categoryOccurrences")) {
                informations = informationService.getAllInformationsSortedByCategoryOccurrences(Sort.by(direction, sortField));
            } else {
                informations = informationService.getInformationRepository()
                        .findAll(Sort.by(direction, sortField));
            }
        }

        model.addAttribute("informations", informations);
        model.addAttribute("categories", categoryService.getCategoryRepository().findAll());

        return "informations";
    }








    @PostMapping("/")
    public String createInformation(@RequestParam String noteName,
                                    @RequestParam String categoryName,
                                    @RequestParam String descriptionNote,
                                    Model model) {
        List<Category> categories = categoryService.getCategoryRepository().findAll();
        Category category = categories.stream()
                .filter(c -> c.getName().equals(categoryName))
                .findFirst()
                .orElse(null);

        if (category == null) {
            category = new Category(categoryName);
            categoryService.getCategoryRepository().save(category);
        }
        Information information = new Information(noteName, descriptionNote,categoryName);
        informationService.getInformationRepository().save(information);
        model.addAttribute("newInformation", information);
        model.addAttribute("informations", informationService.getInformationRepository().findAll());

        return "redirect:/informations/";
    }


    @GetMapping("/category")
    public String getInformationsFromCategory(@RequestParam("categoryName") String category, Model model){
        if(category.isEmpty()){
            model.addAttribute("informations", informationService.getInformationRepository().findAll());
        }
        else {
            model.addAttribute("informations", informationService.getInformationRepository().getInformationByCategory(category, Sort.by(Sort.Direction.ASC, "creationTime")));
             }
        model.addAttribute("categories", categoryService.getCategoryRepository().findAll());
        return "informations";
        }
}