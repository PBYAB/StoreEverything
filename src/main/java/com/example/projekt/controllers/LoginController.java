package com.example.projekt.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/log-in")
public class LoginController {
    @PostMapping ("/")
    public String logIn() {
       return "redirect:/informations/";
    }

}
