package com.example.projekt.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "firstName")
    @Size(min = 3, max = 20, message = "Imię powinno mieć od 3 do 20 znaków")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Imię powinno zaczynać się od dużej litery i zawierać tylko litery")
    private String firstName;

    @Column(name = "lastName")
    @Size(min = 3, max = 50, message = "Nazwisko powinno mieć od 3 do 50 znaków")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Nazwisko powinno zaczynać się od dużej litery i zawierać tylko litery")
    private String lastName;

    @Column(name = "login")
    @Size(min = 3, max = 20, message = "Login powinien mieć od 3 do 20 znaków")
    @Pattern(regexp = "^[a-z]+$", message = "Login powinien składać się tylko z małych liter")
    private String login;

    @Column(name = "password")
    @Size(min = 5, message = "Hasło powinno mieć co najmniej 5 znaków")
    private String password;

    @Column(name = "age")
    @NotNull(message = "Wiek jest wymagany")
    @Min(value = 18, message = "Wiek musi być większy lub równy 18")
    private Integer age;

    public User() {

    }
}
