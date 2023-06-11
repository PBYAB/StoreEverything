package com.example.projekt.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


    @Bean
    public PasswordEncoder passwordEncoder () {
        int rounds = 12;
        return new BCryptPasswordEncoder( rounds);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/user/register","/user/login").permitAll()
                        .requestMatchers("user/admin/**").hasRole("ADMIN")
                        .requestMatchers("user/admin").hasRole("ADMIN")
                        .requestMatchers("/informations/").hasRole("FULL_USER")
                        .requestMatchers("/informations/add-category").hasRole("FULL_USER")
                        .requestMatchers("/informations/add-information").hasRole("FULL_USER")
                        .requestMatchers("/informations/share/**").hasRole("FULL_USER")
                        .requestMatchers("/informations/shared").hasAnyRole("FULL_USER","LIMITED_USER")
                        .requestMatchers("/informations/edit/**").hasRole("FULL_USER")
                        .requestMatchers("/informations/delete/**").hasRole("FULL_USER")
                        .anyRequest().authenticated()

                )
                .formLogin((form) -> form
                        .loginPage("/user/login")
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/informations/")
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
                )
                .logout((logout) -> logout.logoutSuccessUrl("/").permitAll());




        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager (UserDetailsService customUserDetailsService)
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider .setUserDetailsService(customUserDetailsService);
        authProvider .setPasswordEncoder(passwordEncoder());
        List<AuthenticationProvider> providers = List.of(authProvider );
        return new ProviderManager( providers);
    }
}