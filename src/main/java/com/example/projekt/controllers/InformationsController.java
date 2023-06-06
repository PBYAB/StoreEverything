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
                                     @RequestParam(required = false) String filterByStartDate,
                                     @RequestParam(required = false) String filterByEndDate,
                                     @RequestParam(required = false, defaultValue = "false") boolean resetFilters,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model) {

        List<Information> informations;
        Sort.Direction direction = Sort.Direction.DESC;
        String sortField = "creationTime";

        if (resetFilters) {
            model.addAttribute("selectedCategory", null);
            model.addAttribute("selectedSortBy", null);
            model.addAttribute("selectedSortDirection", null);
            model.addAttribute("selectedFilterByStartDate", null);
            model.addAttribute("selectedFilterByEndDate", null);

            removeCookie(response, "categoryName");
            removeCookie(response, "sortBy");
            removeCookie(response, "sortDirection");
            removeCookie(response, "filterByStartDate");
            removeCookie(response, "filterByEndDate");
            return "redirect:/informations/";
        }

        boolean isFilteringOrSorting = categoryName != null || sortBy != null || sortDirection != null || filterByStartDate != null || filterByEndDate != null;
        if (isFilteringOrSorting) {
            model.addAttribute("selectedCategory", categoryName);
            model.addAttribute("selectedSortBy", sortBy);
            model.addAttribute("selectedSortDirection", sortDirection);
            model.addAttribute("selectedFilterByStartDate", filterByStartDate);
            model.addAttribute("selectedFilterByEndDate", filterByEndDate);

            addCookie(response, "categoryName", categoryName);
            addCookie(response, "sortBy", sortBy);
            addCookie(response, "sortDirection", sortDirection);
            addCookie(response, "filterByStartDate", filterByStartDate);
            addCookie(response, "filterByEndDate", filterByEndDate);
        } else {
            categoryName = getCookieValue(request, "categoryName");
            sortBy = getCookieValue(request, "sortBy");
            sortDirection = getCookieValue(request, "sortDirection");
            filterByStartDate = getCookieValue(request, "filterByStartDate");
            filterByEndDate = getCookieValue(request, "filterByEndDate");

            model.addAttribute("selectedCategory", categoryName);
            model.addAttribute("selectedSortBy", sortBy);
            model.addAttribute("selectedSortDirection", sortDirection);
            model.addAttribute("selectedFilterByStartDate", filterByStartDate);
            model.addAttribute("selectedFilterByEndDate", filterByEndDate);
        }

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
                categoryName=null;
            }
        }

        if (categoryName != null && !categoryName.isEmpty()) {
            informations = informationService.getInformationRepository()
                    .findAllByCategory_Name(categoryName, Sort.by(direction, sortField));
        } else {
            if (sortField.equals("categoryOccurrences")) {
                informations = informationService.getAllInformationsSortedByCategoryOccurrences(Sort.by(direction, sortField));
            } else {
                informations = informationService.getInformationRepository()
                        .findAll(Sort.by(direction, sortField));
            }
        }

        if (filterByStartDate != null && filterByEndDate != null && !filterByStartDate.isEmpty() && !filterByEndDate.isEmpty()) {
            informations = filterInformationsByDate(informations, filterByStartDate, filterByEndDate);
        }

        model.addAttribute("informations", informations);
        model.addAttribute("categories", categoryService.getCategoryRepository().findAll());

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
        Information savedInformation = informationService.getInformationRepository().save(information);

        Category existingCategory = categoryService.getCategoryRepository().findByName(information.getCategory().getName());
        if (existingCategory == null) {
            categoryService.getCategoryRepository().save(information.getCategory());
        } else {
            savedInformation.setCategory(existingCategory);
            informationService.getInformationRepository().save(savedInformation);
        }
        return "redirect:/informations/";
    }

    @GetMapping("/category")
    public String getInformationsFromCategory(@RequestParam("categoryName") String category, Model model){
        Category categoryObj = categoryService.getCategoryRepository().findByName(category);
        if (categoryObj != null) {
            model.addAttribute("informations", informationService.getInformationRepository().findAllByCategory_Name(categoryObj.getName(), Sort.by(Sort.Direction.ASC, "creationTime")));
        } else {
            model.addAttribute("informations", informationService.getInformationRepository().findAll());
        }
        return "informations";
    }


    private List<Information> filterInformationsByDate(List<Information> informations, String filterByStartDate, String filterByEndDate) {
        DateTimeFormatter formatterDisplay = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        DateTimeFormatter formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(filterByStartDate, formatterInput);
        LocalDate endDate = LocalDate.parse(filterByEndDate, formatterInput);

        return informations.stream()
                .filter(info -> {
                    LocalDateTime infoDateTime = LocalDateTime.parse(info.getCreationTime(), formatterDisplay);
                    LocalDate infoDate = infoDateTime.toLocalDate();
                    return !infoDate.isBefore(startDate) && !infoDate.isAfter(endDate);
                })
                .collect(Collectors.toList());
    }



    public static String getDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        return dateTime.format(formatter);
    }

    private void addCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

    private void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}