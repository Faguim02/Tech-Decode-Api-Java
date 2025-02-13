package com.techdecode.blog.view.model.post;

import com.techdecode.blog.models.CategoryModel;
import jakarta.validation.constraints.NotNull;

public record PostCreateRequest(
        @NotNull String title,
        @NotNull String bannerUrl,
        @NotNull String description,
        String font,
        CategoryModel category
) {
}
