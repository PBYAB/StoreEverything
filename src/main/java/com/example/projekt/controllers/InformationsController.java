package com.example.projekt.controllers;

import com.example.projekt.data.Information;
import com.example.projekt.services.CategoryService;
import com.example.projekt.services.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/informations")
public class InformationsController {

    @Autowired
    InformationService informationService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public String getInformations(@RequestParam(required = false) String categoryName,
                                  @RequestParam(required = false) String sortBy,
                                  @RequestParam(required = false) String sortDirection,
                                  @RequestParam(required = false) String filterByDate,
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
        if (filterByDate != null && !filterByDate.isEmpty()) {
            informations = filterInformationsByDate(informations, filterByDate);
        }

        model.addAttribute("informations", informations);
        model.addAttribute("categories", categoryService.getCategoryRepository().findAll());

        return "informations";
    }

    private List<Information> filterInformationsByDate(List<Information> informations, String filterByDate) {
        DateTimeFormatter formatterDisplay = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        DateTimeFormatter formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate filterDate = LocalDate.parse(filterByDate, formatterInput);
        return informations.stream()
                .filter(info -> {
                    LocalDateTime infoDateTime = LocalDateTime.parse(info.getCreationTime(), formatterDisplay);
                    LocalDate infoDate = infoDateTime.toLocalDate();
                    return infoDate.isEqual(filterDate);
                })
                .collect(Collectors.toList());
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
