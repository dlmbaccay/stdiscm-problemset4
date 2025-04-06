package com.garynation.frontend_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Return the login template
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // For now, just a simple check
        if ("user".equals(username) && "password".equals(password)) {
            model.addAttribute("username", username);
            return "home";  // If login is successful, redirect to home page
        }
        return "login";  // If login fails, go back to login page
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";  // Render home page template
    }
}