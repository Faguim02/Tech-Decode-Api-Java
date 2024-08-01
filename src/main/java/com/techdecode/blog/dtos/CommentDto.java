package com.techdecode.blog.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CommentDto(@NotBlank String name, @NotBlank String comment, @NotBlank String date, @NotBlank String post_id) {
}
