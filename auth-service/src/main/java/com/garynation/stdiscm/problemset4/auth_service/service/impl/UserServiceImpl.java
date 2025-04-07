package com.garynation.stdiscm.problemset4.auth_service.service.impl;

import com.garynation.stdiscm.problemset4.auth_service.dto.UserDto;
import com.garynation.stdiscm.problemset4.auth_service.entity.User;
import com.garynation.stdiscm.problemset4.auth_service.exception.ResourceNotFoundException;
import com.garynation.stdiscm.problemset4.auth_service.mapper.UserMapper;
import com.garynation.stdiscm.problemset4.auth_service.repository.UserRepository;
import com.garynation.stdiscm.problemset4.auth_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UUID userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        User savedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto deleteUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(userId);
        return null;
    }

    @Override
    public UserDto loginUser(String email, String password) {

        return null;
    }
}
