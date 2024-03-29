package com.example.projekt.services;

import com.example.projekt.data.Authority;
import com.example.projekt.data.User;
import com.example.projekt.repositories.AuthoritiesRepository;
import com.example.projekt.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthoritiesRepository authoritiesRepository;

}
