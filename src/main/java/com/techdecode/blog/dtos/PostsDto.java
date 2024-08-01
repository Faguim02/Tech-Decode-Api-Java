package com.techdecode.blog.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record PostsDto(@NotBlank UUID id, @NotBlank String title, @NotBlank String bannerUrl, @NotBlank String date_at) {
}
