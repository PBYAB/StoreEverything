package com.example.projekt.repositories;

import com.example.projekt.data.Information;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InformationRepository extends JpaRepository<Information,Integer> {
    List<Information> findAllByCategory_Name(String category, Sort sort);
    Void deleteById(int Id);
}
