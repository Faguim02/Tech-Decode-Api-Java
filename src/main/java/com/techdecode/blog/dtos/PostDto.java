package com.techdecode.blog.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PostDto(@NotBlank String title, @NotBlank String bannerUrl, @NotBlank String font, @NotBlank String date, @NotBlank String description, List<CommentDto> comments) {
}
