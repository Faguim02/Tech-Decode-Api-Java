package com.techdecode.blog.view.model.post;

import com.techdecode.blog.models.CategoryModel;
import com.techdecode.blog.models.CommentModel;

import java.util.List;
import java.util.UUID;

public record PostResponseDetails(
        UUID id,
        String title,
        String bannerUrl,
        String description,
        String font,
        String data_at,
        List<CommentModel> comments,
        CategoryModel category
) {
}
