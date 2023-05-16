package com.example.projekt.repositories;

import com.example.projekt.data.Category;
import com.example.projekt.data.Information;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.SessionScope;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Getter
@SessionScope
public class InformationRepository {
    private List<Information> informations = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public InformationRepository(){
        System.out.println("constructor");
        categories.add(new Category("strona www"));
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        informations.add(new Information("1","pb.com","wazna strona",new Category("strona www"), formattedDateTime));
    }
}
