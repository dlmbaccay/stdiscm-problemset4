package com.garynation.stdiscm.problemset4.auth_service.service;

import com.garynation.stdiscm.problemset4.auth_service.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(UUID userId);
    UserDto getUserByEmail(String email);
    List<UserDto> getAllUsers();
    UserDto updateUser(UUID userId, UserDto userDto);
    UserDto deleteUser(UUID userId);

    UserDto loginUser(String email, String password);

}
