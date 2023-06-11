package com.example.projekt.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "login")
    @Size(min = 3, max = 20, message = "Login powinien mieć od 3 do 20 znaków")
    @Pattern(regexp = "^[a-z]+$", message = "Login powinien składać się tylko z małych liter")
    private String login;

    @Column(name = "authority", nullable = false, length = 50)
    private String authority;


    public Authority() {
    }

    public Authority(String login, String authority) {
        this.login = login;
        this.authority = authority;
    }
}
