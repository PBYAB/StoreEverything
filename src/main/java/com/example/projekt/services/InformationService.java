package com.example.projekt.services;

import com.example.projekt.data.Category;
import com.example.projekt.data.Information;
import com.example.projekt.repositories.CategoryRepository;
import com.example.projekt.repositories.InformationRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
public class InformationService {

    @Autowired
    private InformationRepository informationRepository;


    @Autowired
    private CategoryRepository categoryRepository;

    public List<Information> getAllInformations() {
        return informationRepository.getInformations();
    }


    public List<Information> getInformationsByCategoryName(String categoryName) {
        List<Information> informations = new ArrayList<>();
        for (Information information : getAllInformations()) {
            if (information.getCategory().getName().equals(categoryName)) {
                informations.add(information);
            }
        }
        return informations;
    }

}
