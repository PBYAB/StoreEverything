package com.example.projekt.controllers;

import com.example.projekt.data.User;
import com.example.projekt.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.getUserRepository().save(user);
        return "redirect:login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("login") String login, @RequestParam("password") String password, Model model) {
        User user = userService.getUserRepository().getUserByLoginAndPassword(login, password);

        if (user == null) {
            model.addAttribute("noUser", "Niepoprawne dane");
            model.addAttribute("user", new User());
            return "login";
        }
        return "redirect:/informations/";
    }
}