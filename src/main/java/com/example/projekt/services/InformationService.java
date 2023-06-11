package com.example.projekt.services;

import com.example.projekt.data.Information;
import com.example.projekt.repositories.InformationRepository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Getter
public class InformationService {
    @Autowired
    InformationRepository informationRepository;

    public InformationService(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;

    }

    public List<Information> getAllInformationsSortedByCategoryOccurrences(Sort sort, String login, boolean isShared) {
        List<Information> informations = informationRepository.findAllByLoginAndShared(login,isShared);
        informations.sort(Comparator.comparingInt(o -> getCategoryOccurrences(o.getCategory().getName(), informations)));
        if (sort.getOrderFor("categoryOccurrences").isDescending()) {
            Collections.reverse(informations);
        }
        return informations;
    }


    private int getCategoryOccurrences(String category, List<Information> informations) {
        int count = 0;
        for (Information information : informations) {
            if (information.getCategory().getName().equals(category)) {
                count++;
            }
        }return count;
    }

}


