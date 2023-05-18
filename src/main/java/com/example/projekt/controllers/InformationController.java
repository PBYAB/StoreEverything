package com.example.projekt.controllers;

import com.example.projekt.data.Category;
import com.example.projekt.data.Information;
import com.example.projekt.services.CategoryService;
import com.example.projekt.services.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping(value = "/informations")
public class InformationController {
    @Autowired
    InformationService informationService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public String getInformations(@RequestParam(required = false) String categoryName, Model model) {
        List<Information> informations;
        if (categoryName != null && !categoryName.isEmpty()) {
            informations = informationService.getInformationsByCategoryName(categoryName);
        } else {
            informations = informationService.getAllInformations();
        }
        model.addAttribute("informations", informations);
        model.addAttribute("categories", informationService.getCategoryRepository().getCategories());
        return "informations";
    }



    @PostMapping("/")
    public String createInformation(@RequestParam String noteName,
                                    @RequestParam String categoryName,
                                    @RequestParam String descriptionNote,
                                    Model model) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        List<Category> categories = categoryService.getCategoryRepository().getCategories();
        Category category = categories.stream()
                .filter(c -> c.getName().equals(categoryName))
                .findFirst()
                .orElse(null);

        if (category == null) {
            category = new Category(categoryName);
            categories.add(category);
        }
        Information information = new Information(Integer.toString(informationService.getInformationRepository().getInformations().size() + 1), noteName, categoryName, category, formattedDateTime);
        informationService.getInformationRepository().getInformations().add(information);
        model.addAttribute("newInformation", information);
        model.addAttribute("informations", informationService.getAllInformations());

        return "redirect:/informations/";
    }


    @GetMapping("/category")
    public String getInformationsFromCategory(@RequestParam("categoryName") String category, Model model){
        if(category.isEmpty()){
            model.addAttribute("informations",informationService.getAllInformations());
            model.addAttribute("categories",informationService.getCategoryRepository().getCategories());
        }
        else
        model.addAttribute("informations",informationService.getInformationsByCategoryName(category));
        model.addAttribute("categories",informationService.getCategoryRepository().getCategories());
        return "informations";
        }

}
