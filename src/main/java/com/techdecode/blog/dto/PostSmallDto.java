package com.techdecode.blog.dto;

import java.util.UUID;

public record PostSmallDto(
        UUID id,
        String title,
        String bannerUrl,
        String date_at
) {
}
