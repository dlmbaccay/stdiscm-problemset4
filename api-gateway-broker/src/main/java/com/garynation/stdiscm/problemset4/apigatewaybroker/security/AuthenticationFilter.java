package com.garynation.stdiscm.problemset4.apigatewaybroker.security;

import com.garynation.stdiscm.problemset4.apigatewaybroker.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private MessageService messageService;

    // List of paths that don't require authentication
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
        "/api/messages/auth",
        "/api/auth/login",
        "/api/auth/register"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) 
            throws ServletException, IOException {
        
        // Skip authentication for excluded paths
        if (isExcludedPath(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Check if auth header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        // Extract token
        String token = authHeader.substring(7);

        try {
            // Create validation request for auth service
            Map<String, Object> validationRequest = new HashMap<>();
            validationRequest.put("action", "validateToken");
            validationRequest.put("token", token);

            // Send validation request to auth service and wait for response
            CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
            messageService.sendToAuthServiceWithCallback(validationRequest, future);

            // Wait for response with timeout
            Map<String, Object> validationResponse = future.get();

            if (validationResponse != null && Boolean.TRUE.equals(validationResponse.get("valid"))) {
                // Add validated user info to request attributes
                request.setAttribute("username", validationResponse.get("username"));
                request.setAttribute("userId", validationResponse.get("userId"));
                request.setAttribute("roles", validationResponse.get("roles"));
                
                // Continue with the filter chain
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Invalid token");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write("Error validating token");
        }
    }

    private boolean isExcludedPath(String requestURI) {
        return EXCLUDED_PATHS.stream()
                .anyMatch(path -> requestURI.startsWith(path));
    }
}