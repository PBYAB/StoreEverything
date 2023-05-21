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
        informationRepository.save(new Information("Spotkanie zespołu", "Planowane spotkanie zespołu projektowego", "Zarządzanie projektami","22.05.2023 23:12:04"));
        informationRepository.save(new Information("Prezentacja sprzedażowa", "Przygotowanie prezentacji dotyczącej nowego produktu", "Prezentacja sprzedażowa","20.05.2023 23:12:04"));
        informationRepository.save(new Information("Raport finansowy", "Przygotowanie raportu finansowego za pierwszy kwartał", "Finanse"));
        informationRepository.save(new Information("Raport finansowy", "Przygotowanie raportu finansowego za pierwszy kwartał", "Finanse"));
        informationRepository.save(new Information("Szkolenie z obsługi systemu", "Szkolenie dla pracowników dotyczące nowego systemu informatycznego", "Zarządzanie IT","19.05.2023 23:12:04"));
        informationRepository.save(new Information("Projekt badawczy", "Realizacja projektu badawczego w dziedzinie biologii", "Nauka","20.05.2023 23:12:04"));
        informationRepository.save(new Information("Projekt badawczy", "Realizacja projektu badawczego w dziedzinie biologii", "Nauka"));
        informationRepository.save(new Information("Projekt badawczy", "Realizacja projektu badawczego w dziedzinie biologii", "Nauka"));
    }

    public List<Information> getAllInformationsSortedByCategoryOccurrences(Sort sort) {
        List<Information> informations = informationRepository.findAll();
        informations.sort(Comparator.comparingInt(o -> getCategoryOccurrences(o.getCategory(), informations)));
        if (sort.getOrderFor("categoryOccurrences").isDescending()) {
            Collections.reverse(informations);
        }
        return informations;
    }


    private int getCategoryOccurrences(String category, List<Information> informations) {
        int count = 0;
        for (Information information : informations) {
            if (information.getCategory().equals(category)) {
                count++;
            }
        }return count;
    }

}


