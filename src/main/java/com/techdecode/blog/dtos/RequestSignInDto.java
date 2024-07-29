package com.techdecode.blog.dtos;

import jakarta.validation.constraints.NotBlank;

public record RequestSignInDto(@NotBlank String email, @NotBlank String password) {
}
