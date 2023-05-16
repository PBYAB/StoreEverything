package com.example.projekt.controllers;

import com.example.projekt.data.Information;
import com.example.projekt.services.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/informations")
public class InformationController {
    @Autowired
    InformationService informationService;

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
        Information information = informationService.createInformation(noteName, categoryName, descriptionNote);
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
