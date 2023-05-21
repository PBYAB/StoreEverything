package com.example.projekt.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Informations_TBL")
public class Information {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    private String category;
    private String creationTime;

    public Information(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        this.creationTime = formattedDateTime;
    }

    public Information(String name, String description, String category, String creationTime) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.creationTime = creationTime;
    }
}
