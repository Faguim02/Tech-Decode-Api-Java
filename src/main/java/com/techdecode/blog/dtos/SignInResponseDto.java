package com.techdecode.blog.dtos;

import jakarta.validation.constraints.NotBlank;

public record SignInResponseDto(@NotBlank String name, @NotBlank String token) {
}
