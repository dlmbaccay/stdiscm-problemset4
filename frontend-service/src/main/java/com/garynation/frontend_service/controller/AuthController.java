package com.garynation.frontend_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/courses")
    public String coursesPage() {
        return "courses";
    }

    

//    @PostMapping("/auth/register")
//    public String register(@RequestParam String username, @RequestParam String password, Model model) {
//        // For now, just a simple check
//        if ("user".equals(username) && "password".equals(password)) {
//            model.addAttribute("username", username);
//            return "home";  // If login is successful, redirect to home page
//        }
//        return "register";  // If login fails, go back to login page
//    }


}