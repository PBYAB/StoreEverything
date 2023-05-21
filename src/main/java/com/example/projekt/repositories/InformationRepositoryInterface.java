package com.example.projekt.repositories;

import com.example.projekt.data.Information;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InformationRepositoryInterface extends JpaRepository<Information,Integer> {
    List<Information> getInformationByCategory(String category, Sort sort);

}
