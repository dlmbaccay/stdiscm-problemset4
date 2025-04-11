package com.garynation.stdiscm.problemset4.auth_service.dto;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithTokenDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String token;
    private UserDetails details;

}
