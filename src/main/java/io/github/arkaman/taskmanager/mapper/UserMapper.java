package io.github.arkaman.taskmanager.mapper;

import io.github.arkaman.taskmanager.domain.dto.UserDto;
import io.github.arkaman.taskmanager.domain.entity.AppUser;

public class UserMapper {
    public static UserDto toDto(AppUser user) {
        return new UserDto(
                user.getUsername(),
                user.getEmail()
        );
    }
}
