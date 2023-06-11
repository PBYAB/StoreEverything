package com.example.projekt.controllers;

import com.example.projekt.data.Authority;
import com.example.projekt.data.User;
import com.example.projekt.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginFormPost(@RequestParam("login") String login, @RequestParam("password") String password, Model model) {
        User user = userService.getUserRepository().findByLogin(login).orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Invalid login or password");
            return "login";
        }

        return "redirect:/informations/";
    }


    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerFormPost(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        User existingUser = userService.getUserRepository().findByLogin(user.getLogin()).orElse(null);
        if (existingUser != null) {
            model.addAttribute("error", "User with this login already exists");
            return "register";
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Authority authority = new Authority(user.getLogin(), "ROLE_LIMITED_USER");
        userService.getAuthoritiesRepository().save(authority);
        userService.getUserRepository().save(user);

        return "redirect:/user/login";
    }


    @GetMapping("/admin")
    public String adminForm(Model model) {
        model.addAttribute("authorities", userService.getAuthoritiesRepository().findAllByAuthorityIsNot("ROLE_ADMIN"));
        model.addAttribute("authority",new Authority());
        return "admin";
    }

    @PostMapping("/admin")
    public String adminManager() {
        return "redirect:/admin/users";
    }
    @PostMapping("/admin/update-role")
    public String updateRole(@RequestParam("login") String login, @RequestParam("newRole") String newRole) {
        Authority authority = userService.getAuthoritiesRepository().findByLogin(login);
        System.out.println(login);
        if (authority != null) {
            authority.setAuthority(newRole);
            userService.getAuthoritiesRepository().save(authority);
        }
        return "redirect:/user/admin";
    }
}
