package com.example.projekt.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Categories")
public class Category {
    @NonNull
    @Size(min = 3, max = 20, message = "Kategoria powinna mieć od {min} do {max} znaków.")
    @Pattern(regexp = "^[a-z]+$", message = "Nazwa kategorii może składać się tylko z małych liter.")
    private String name;

    public Category(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private int id;

    public Category() {

    }
}
