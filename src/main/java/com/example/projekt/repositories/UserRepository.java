package com.example.projekt.repositories;

import com.example.projekt.data.Information;
import com.example.projekt.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByLogin(String login);

    Optional<User> findByLoginAndPassword(String login, String password);
    List<User> findAllByLoginIsNotAndLoginIsNot(String login, String login2);
}
