package com.example.projekt.controllers;

import com.example.projekt.data.Category;
import com.example.projekt.data.Information;
import com.example.projekt.services.CategoryServiceWithJpa;
import com.example.projekt.services.InformationServiceWithJpa;
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
    InformationServiceWithJpa informationServiceWithJpa;

    @Autowired
    CategoryServiceWithJpa categoryServiceWithJpa;

    @GetMapping("/")
    public String getInformations(@RequestParam(required = false) String categoryName, @RequestParam(required = false) String sortDirection, Model model) {
        List<Information> informations;
        Sort.Direction direction = Sort.Direction.DESC; // Default sort direction

        if (sortDirection != null && sortDirection.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        if (categoryName != null && !categoryName.isEmpty()) {
            informations = informationServiceWithJpa.getInformationRepositoryInterface().getInformationByCategory(categoryName, Sort.by(direction, "creationTime"));
        } else {
            informations = informationServiceWithJpa.getInformationRepositoryInterface().findAll(Sort.by(direction, "creationTime"));
        }

        model.addAttribute("informations", informations);
        model.addAttribute("categories", categoryServiceWithJpa.getCategoryRepositoryInterface().findAll());

        return "informations";
    }




    @PostMapping("/")
    public String createInformation(@RequestParam String noteName,
                                    @RequestParam String categoryName,
                                    @RequestParam String descriptionNote,
                                    Model model) {
        List<Category> categories = categoryServiceWithJpa.getCategoryRepositoryInterface().findAll();
        Category category = categories.stream()
                .filter(c -> c.getName().equals(categoryName))
                .findFirst()
                .orElse(null);

        if (category == null) {
            category = new Category(categoryName);
            categoryServiceWithJpa.getCategoryRepositoryInterface().save(category);
        }
        Information information = new Information(noteName, descriptionNote,categoryName);
        informationServiceWithJpa.getInformationRepositoryInterface().save(information);
        model.addAttribute("newInformation", information);
        model.addAttribute("informations", informationServiceWithJpa.getInformationRepositoryInterface().findAll());

        return "redirect:/informations/";
    }


    @GetMapping("/category")
    public String getInformationsFromCategory(@RequestParam("categoryName") String category, Model model){
        if(category.isEmpty()){
            model.addAttribute("informations",informationServiceWithJpa.getInformationRepositoryInterface().findAll());
        }
        else {
            model.addAttribute("informations",informationServiceWithJpa.getInformationRepositoryInterface().getInformationByCategory(category, Sort.by(Sort.Direction.ASC, "creationTime")));
             }
        model.addAttribute("categories", categoryServiceWithJpa.getCategoryRepositoryInterface().findAll());
        return "informations";
        }
}