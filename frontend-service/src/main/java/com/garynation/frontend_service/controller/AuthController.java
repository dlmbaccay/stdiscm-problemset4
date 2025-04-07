package com.garynation.frontend_service.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Return the login template
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";  // Return the login template
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        System.out.println("Logging in user: " + username + " with password: " + password);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/auth/login";
        String response = restTemplate.postForObject(url, new HashMap<String, String>() {{
            put("username", username);
            put("password", password);
        }}, String.class);
        System.out.println("Response from auth service: " + response);
        // For now, just a simple check
        // if ("user".equals(username) && "password".equals(password)) {
        //     model.addAttribute("username", username);
        //     return "home";  // If login is successful, redirect to home page
        // }
        return "login";  // If login fails, go back to login page
    }

    @PostMapping("/auth/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        // For now, just a simple check
        if ("user".equals(username) && "password".equals(password)) {
            model.addAttribute("username", username);
            return "home";  // If login is successful, redirect to home page
        }
        return "register";  // If login fails, go back to login page
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";  // Render home page template
    }
}