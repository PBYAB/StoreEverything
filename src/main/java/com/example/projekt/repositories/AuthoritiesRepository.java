package com.example.projekt.repositories;

import com.example.projekt.data.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthoritiesRepository extends
        JpaRepository<Authority, Integer> {
    Authority findByLogin(String login);

    List<Authority> findAllByAuthorityIsNot(String authority);

}
