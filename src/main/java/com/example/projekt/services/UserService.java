package com.example.projekt.services;

import com.example.projekt.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class UserService {

    @Autowired
    UserRepository userRepository;

}
