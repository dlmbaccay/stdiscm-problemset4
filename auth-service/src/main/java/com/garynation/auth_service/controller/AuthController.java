package com.garynation.auth_service.controller;

import com.garynation.auth_service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Authenticate the user (use your user service here)
        if ("user".equals(username) && "password".equals(password)) {
            return jwtUtil.generateToken(username);  // Generate and return JWT token
        }
        return "Invalid credentials";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        // Register the user (this could save user data to a database)
        return "User registered successfully";
    }
}
