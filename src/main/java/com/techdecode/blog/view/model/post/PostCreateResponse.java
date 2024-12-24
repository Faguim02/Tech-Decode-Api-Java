package com.techdecode.blog.view.model.post;

import java.util.UUID;

public record PostCreateResponse(
        UUID id,
        String title,
        String bannerUrl,
        String description,
        String font,
        String date_at
) {
}
