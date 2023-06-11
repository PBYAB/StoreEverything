package com.example.projekt.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@Entity
@Table(name = "information")
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NonNull
    @Size(min = 3, max = 20, message = "Nazwa powinna mieć od {min} do {max} znaków.")
    @Column(name = "name",nullable = false,length = 20)
    private String name;

    @NonNull
    @Column(name = "description",nullable = false,length = 500)
    @Size(min = 5, max = 500, message = "Opis powinien mieć od {min} do {max} znaków.")
    private String description;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @Column(name = "creation_time")
    private String creationTime;

    @Column(name = "link")
    private String link;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "shared", columnDefinition = "boolean default false")
    private boolean shared;


    public Information(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        this.creationTime = dateTime.format(formatter);
    }


    public Information(String name, String description, Category category, String creationTime) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.creationTime = creationTime;
    }

    public Information() {
    }
}
