package com.techdecode.blog.dto;

import com.techdecode.blog.models.PostModel;

import java.util.List;
import java.util.UUID;

public record CategoryDto(
        UUID id,
        String title,
        List<PostModel> postModels
) {
}
