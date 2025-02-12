package com.techdecode.blog.view.model.post;

import java.util.UUID;

public record PostResponse(
        UUID id,
        String title,
        String bannerUrl,
        String date_at
) {
}
