package com.example.projekt.controllers;

import com.example.projekt.data.Category;
import com.example.projekt.data.Information;
import com.example.projekt.services.CategoryService;
import com.example.projekt.services.InformationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/informations")
public class InformationsController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String getInformationsGet(@RequestParam(required = false) String categoryName,
                                     @RequestParam(required = false) String sortBy,
                                     @RequestParam(required = false) String sortDirection,
                                     @RequestParam(required = false) String filterByDate,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model) {

        List<Information> informations;
        Sort.Direction direction = Sort.Direction.DESC;
        String sortField = "creationTime";

        if (sortDirection != null && sortDirection.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }
        // Sprawdź, czy w ciasteczku są zapisane wartości sortowania i filtrowania
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("sortBy")) {
                    sortBy = cookie.getValue();
                } else if (cookie.getName().equals("sortDirection")) {
                    sortDirection = cookie.getValue();
                }
            }
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

        // Zapisz wartości sortowania i filtrowania do ciasteczek
        response.addCookie(new Cookie("sortBy", sortBy));
        response.addCookie(new Cookie("sortDirection", sortDirection));
        response.addCookie(new Cookie("filterByDate", filterByDate));

        return "informations";
    }

    @PostMapping("/")
    public String getInformationsPost(@Valid @ModelAttribute Information information, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("information", information);
            return "add-information";
        }
        information.setCreationTime(getDate());
        informationService.getInformationRepository().save(information);
        categoryService.getCategoryRepository().save(new Category(information.getCategory()));
        return "redirect:/informations/";
    }

    private List<Information> filterInformationsByDate(List<Information> informations, String filterByDate) {
        DateTimeFormatter formatterDisplay = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        DateTimeFormatter formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(filterByDate, formatterInput);
        LocalDate currentDate = LocalDate.now();

        return informations.stream()
                .filter(info -> {
                    LocalDateTime infoDateTime = LocalDateTime.parse(info.getCreationTime(), formatterDisplay);
                    LocalDate infoDate = infoDateTime.toLocalDate();
                    return !infoDate.isBefore(startDate) && !infoDate.isAfter(currentDate);
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

    public static String getDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);

        return formattedDateTime;
    }
}