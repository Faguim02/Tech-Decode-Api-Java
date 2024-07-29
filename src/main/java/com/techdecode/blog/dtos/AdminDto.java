package com.techdecode.blog.dtos;

import jakarta.validation.constraints.NotBlank;

public record AdminDto(@NotBlank String name, @NotBlank String email, @NotBlank String password) {
}
