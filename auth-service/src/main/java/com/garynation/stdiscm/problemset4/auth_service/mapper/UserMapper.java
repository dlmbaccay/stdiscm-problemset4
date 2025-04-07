package com.garynation.stdiscm.problemset4.auth_service.mapper;

import com.garynation.stdiscm.problemset4.auth_service.dto.UserDto;
import com.garynation.stdiscm.problemset4.auth_service.entity.User;

public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getPassword()
        );
    }

    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getRole(),
                userDto.getPassword()
        );
    }

}
