package com.example.projekt.controllers;

import com.example.projekt.data.Category;
import com.example.projekt.data.Information;
import com.example.projekt.services.CategoryService;
import com.example.projekt.services.InformationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/informations")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/add-category")
    public String addCategoryGet(Model model) {
        model.addAttribute("category", new Category());
        return "add-category";
    }

    @PostMapping("/add-category")
    public String addCategoryPost(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-category";
        }
        categoryService.getCategoryRepository().save(category);
        return "redirect:/informations/";
    }

    @GetMapping("/edit/{id}")
    public String editInformation(@PathVariable("id") int id, Model model) {
        Information information = informationService.getInformationRepository()
                .findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("Invalid information id: " + id));
        model.addAttribute("information", information);
        return "information";
    }

    @GetMapping("/add-information")
    public String addInformationGet(Model model) {
        model.addAttribute("information", new Information());
        return "add-information";
    }

    @PostMapping("/add-information")
    public String addInformationPost(@Valid @ModelAttribute Information information, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-information";
        }
        informationService.getInformationRepository().save(information);
        return "redirect:/informations/";
    }

    @PostMapping("/save")
    public String updateInformation(@Valid @ModelAttribute("information") Information updatedInformation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "information";
        }
        Information existingInformation = informationService.getInformationRepository().findById(updatedInformation.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid information id: " + updatedInformation.getId()));

        existingInformation.setCategory(updatedInformation.getCategory());
        existingInformation.setDescription(updatedInformation.getDescription());
        existingInformation.setCreationTime(getDate());

        informationService.getInformationRepository().save(existingInformation);
        return "redirect:/informations/";
    }

    @GetMapping("/delete/{id}")
    public String deleteInformation(@PathVariable("id") int id) {
        informationService.getInformationRepository().deleteById(id);
        return "redirect:/informations/";
    }

    public static String getDate() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
}
