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


    @GetMapping("/edit/{id}")
    public String editInformation(@PathVariable("id") Long id, Model model) {
        Information information = informationService.getInformationRepository().findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("Invalid information id: " + id));
        model.addAttribute("information", information);
        return "information";
    }

    @PostMapping("/save")
    public String updateInformation(@ModelAttribute("information") Information updatedInformation) {
        Information existingInformation = informationService.getInformationRepository().findById((int) updatedInformation.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid information id: " + updatedInformation.getId()));

        existingInformation.setCategory(updatedInformation.getCategory());
        existingInformation.setDescription(updatedInformation.getDescription());

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        existingInformation.setCreationTime(formattedDateTime);

        informationService.getInformationRepository().save(existingInformation);
        return "redirect:/informations/";
    }
}