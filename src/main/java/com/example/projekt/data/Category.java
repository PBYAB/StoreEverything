package com.example.projekt.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Categories_TBL")
public class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private Long id;
}
