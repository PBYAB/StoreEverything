package com.example.projekt.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Informations")
public class Information {

    @Id
    @NonNull
    @GeneratedValue
    private int id;

    @NonNull
    @Size(min = 3, max = 20, message = "Nazwa powinna mieć od {min} do {max} znaków.")
    private String name;

    @NonNull
    @Size(min = 5, max = 500, message = "Opis powinien mieć od {min} do {max} znaków.")
    private String description;

    @NonNull
    @Size(min = 3, max = 20, message = "Kategoria powinna mieć od {min} do {max} znaków.")
    @Pattern(regexp = "^[a-z]+$", message = "Nazwa kategorii może składać się tylko z małych liter.")
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

    public Information() {

    }
}
