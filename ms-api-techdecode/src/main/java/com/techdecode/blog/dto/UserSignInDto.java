package com.techdecode.blog.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserSignInDto(
        String email,
        String password
) {
}
