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
    public String addCategoryPost(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-category";
        }
        Category existingCategory = categoryService.getCategoryRepository().findByName(category.getName());
        if (existingCategory != null) {
            model.addAttribute("categoryExistsError", "Category already exists");
            return "add-category";
        }
        categoryService.getCategoryRepository().save(category);
        return "redirect:/informations/";
    }


    @GetMapping("/edit/{id}")
    public String editInformationGet(@PathVariable("id") int id, Model model) {
        Information information = informationService.getInformationRepository()
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid information id: " + id));
        model.addAttribute("information", information);
        model.addAttribute("categories", categoryService.getCategoryRepository().findAll());
        return "edit-information";
    }

    @PostMapping("/edit/{id}")
    public String editInformationPost(@Valid @ModelAttribute("information") Information updatedInformation, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategoryRepository().findAll());
            return "edit-information";
        }
        Information existingInformation = informationService.getInformationRepository().findById(updatedInformation.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid information id: " + updatedInformation.getId()));

        existingInformation.setName(updatedInformation.getName());
        if(updatedInformation.getLink().isBlank())
            existingInformation.setLink(null);
        else
            existingInformation.setLink(updatedInformation.getLink());

        existingInformation.setCategory(categoryService.getCategoryRepository().findById(updatedInformation.getCategory().getId()).orElse(null));
        existingInformation.setDescription(updatedInformation.getDescription());
        existingInformation.setCreationTime(getDate());

        informationService.getInformationRepository().save(existingInformation);
        return "redirect:/informations/";
    }


    @GetMapping("/add-information")
    public String addInformationGet(Model model) {
        model.addAttribute("information", new Information());
        model.addAttribute("categories", categoryService.getCategoryRepository().findAll());
        return "add-information";
    }


    @PostMapping("/add-information")
    public String addInformationPost(@Valid @ModelAttribute Information information, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategoryRepository().findAll());
            return "add-information";
        }
        if (information.getCategory() == null) {
            model.addAttribute("categories", categoryService.getCategoryRepository().findAll());
            return "add-information";
        }
        information.setCategory(categoryService.getCategoryRepository().findById(information.getCategory().getId()).orElse(null));
        information.setCreationTime(getDate());
        if(information.getLink().isBlank())
            information.setLink(null);
        informationService.getInformationRepository().save(information);
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