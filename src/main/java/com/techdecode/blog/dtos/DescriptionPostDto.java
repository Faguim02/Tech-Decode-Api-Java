package com.techdecode.blog.dtos;

import jakarta.validation.constraints.NotBlank;

public record DescriptionPostDto(@NotBlank String type, @NotBlank String description, @NotBlank PostDto post) {
}
