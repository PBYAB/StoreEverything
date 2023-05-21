package com.example.projekt.services;

import com.example.projekt.repositories.InformationRepositoryInterface;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class InformationServiceWithJpa {
    @Autowired
    InformationRepositoryInterface informationRepositoryInterface;

    public InformationServiceWithJpa(InformationRepositoryInterface informationRepositoryInterface) {
        this.informationRepositoryInterface = informationRepositoryInterface;
    }
}
