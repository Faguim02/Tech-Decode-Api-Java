package com.techdecode.blog.dto;

import java.util.List;
import java.util.UUID;

public record PostDto(
        UUID id,
        String title,
        String bannerUrl,
        String description,
        String font,
        String data_at,
        List<Object> comments,
        CategoryDto category
) {

}
