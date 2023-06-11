package com.example.projekt.security;

import com.example.projekt.data.Authority;
import com.example.projekt.data.User;
import com.example.projekt.repositories.AuthoritiesRepository;
import com.example.projekt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsersAuthDBService implements UserDetailsService {
    @Autowired
    private UserRepository users;

    @Autowired
    private AuthoritiesRepository authorities;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = users.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user login: " + login));;
        if (user == null) {
            throw new UsernameNotFoundException("User " + login + " not found!");
        } else {
            Authority authority = authorities.findByLogin(login);

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getLogin(),
                    user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(authority.getAuthority()))
            );
            return userDetails;
        }
    }
}
