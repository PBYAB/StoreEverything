package com.example.projekt.repositories;

import com.example.projekt.data.Information;
import com.example.projekt.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User getUserByLoginAndPassword(String login, String password);
}
