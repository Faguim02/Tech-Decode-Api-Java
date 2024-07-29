package com.techdecode.blog.dtos;

import jakarta.validation.constraints.NotBlank;

public record CommentDto(@NotBlank String name, @NotBlank String comment, @NotBlank String date, @NotBlank PostDto post) {
}
