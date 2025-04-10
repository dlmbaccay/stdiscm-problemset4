package com.garynation.stdiscm.problemset4.auth_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.garynation.stdiscm.problemset4.auth_service.dto.RefreshDto;
import com.garynation.stdiscm.problemset4.auth_service.dto.UserDto;
import com.garynation.stdiscm.problemset4.auth_service.dto.UserLoginDto;
import com.garynation.stdiscm.problemset4.auth_service.dto.UserWithTokenDto;
import com.garynation.stdiscm.problemset4.auth_service.repository.UserRepository;
import com.garynation.stdiscm.problemset4.auth_service.security.JwtUtil;
import com.garynation.stdiscm.problemset4.auth_service.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserController {
    private UserService userService;

    @Operation(summary = "Get user by ID")
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") UUID userId) {

        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
        UserDto user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Update user by ID")
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") UUID userId, @RequestBody UserDto userDto) {
        UserDto user = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Delete user by ID")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted succesfully.");
    }

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;
    @Operation(summary = "Authenticate user")
    @PostMapping("/login")
    public ResponseEntity<UserWithTokenDto> authenticateUser(@RequestBody UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userLoginDto.getEmail(),
                userLoginDto.getPassword()
            )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails.getUsername());
        
        UserDto user = userService.getUserByEmail(userDetails.getUsername());
        UserWithTokenDto userWithTokenDto = new UserWithTokenDto(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getRole(),
            token
        );
    
        return new ResponseEntity<>(userWithTokenDto, HttpStatus.OK);
    }
    @Operation(summary = "Create user, omit id")
    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody RefreshDto refreshDto) {
        // Validate the refresh token
        if (jwtUtils.validateJwtToken(refreshDto.getRefreshToken())) {
            // Extract username from the refresh token
            String username = jwtUtils.getEmailFromToken(refreshDto.getRefreshToken());
            // Generate a new access token
            String newAccessToken = jwtUtils.generateToken(username);
            return new ResponseEntity<>(newAccessToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid refresh token", HttpStatus.UNAUTHORIZED);
        }
    }
}
