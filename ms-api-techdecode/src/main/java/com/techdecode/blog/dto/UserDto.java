package com.techdecode.blog.dto;

import com.techdecode.blog.models.roles.UserRole;

import java.util.UUID;

public record UserDto(
        UUID id,
        String name,
        String email,
        String password,
        UserRole userRole
) {

}
