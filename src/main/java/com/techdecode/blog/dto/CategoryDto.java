package com.techdecode.blog.dto;

import java.util.List;
import java.util.UUID;

public record CategoryDto(
        UUID id,
        String title,
        List<PostSmallDto> postModels
) {
}
