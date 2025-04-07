package com.garynation.frontend_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/auth/login", "/register", "/auth/register").permitAll()  // Allow public access to login and register pages
                .anyRequest().authenticated()  // All other requests require authentication
            )
            .formLogin(form -> form.loginPage("/login").permitAll())  // Custom login page
            .logout(logout -> logout.permitAll());  // Enable logout functionality
            
        return http.build();
    }
}