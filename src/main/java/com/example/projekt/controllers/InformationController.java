package com.example.projekt.controllers;

import com.example.projekt.data.Category;
import com.example.projekt.data.Information;
import com.example.projekt.repositories.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/informations")
public class InformationController {
    @Autowired
    InformationRepository informationRepository;

    @GetMapping("/")
    public String getInformations(Model model){
        model.addAttribute("informations",informationRepository.getInformations());
        return "informations";
    }
    @PostMapping("/")
    public String createInformation(@RequestParam String noteName,
                                    @RequestParam String categoryName,
                                    @RequestParam String descriptionNote,
                                    Model model) {
        Information information = new Information(Integer.toString(informationRepository.getInformations().size() + 1) ,noteName,categoryName,new Category(descriptionNote));
        informationRepository.getInformations().add(information);
        model.addAttribute("newInformation", information);
        model.addAttribute("informations", informationRepository.getInformations());
        return "redirect:/informations/";
    }
}