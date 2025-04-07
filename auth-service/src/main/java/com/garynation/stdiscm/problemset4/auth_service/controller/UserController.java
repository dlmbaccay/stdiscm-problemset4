package com.garynation.stdiscm.problemset4.auth_service.controller;

import com.garynation.stdiscm.problemset4.auth_service.dto.UserDto;
import com.garynation.stdiscm.problemset4.auth_service.entity.User;
import com.garynation.stdiscm.problemset4.auth_service.repository.UserRepository;
import com.garynation.stdiscm.problemset4.auth_service.security.JwtUtil;
import com.garynation.stdiscm.problemset4.auth_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserController {
    private UserService userService;



    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") UUID userId) {

        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") UUID userId, @RequestBody UserDto userDto) {
        UserDto user = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID userId) {
        UserDto user = userService.deleteUser(userId);
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
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails.getUsername());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


}
