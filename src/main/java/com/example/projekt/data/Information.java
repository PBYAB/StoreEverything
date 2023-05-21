package com.example.projekt.data;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

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
    private String descirption;
    private String category;
    private String creationTime;

    public Information(String name, String descirption, String category) {
        this.name = name;
        this.descirption = descirption;
        this.category = category;
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        this.creationTime = formattedDateTime;
    }

    public Information(String name, String descirption, String category, String creationTime) {
        this.name = name;
        this.descirption = descirption;
        this.category = category;
        this.creationTime = creationTime;
    }
}
