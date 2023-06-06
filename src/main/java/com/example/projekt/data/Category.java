package com.example.projekt.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @NonNull
    @Size(min = 3, max = 20, message = "Kategoria powinna mieć od {min} do {max} znaków.")
    @Pattern(regexp = "^[a-z]+$", message = "Nazwa kategorii może składać się tylko z małych liter.")
    @Column(name= "name", nullable = false, length = 20)
    private String name;

    public Category(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", nullable = false)
    private int id;

    public Category() {
    }
}
