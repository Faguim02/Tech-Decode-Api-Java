package com.techdecode.blog.view.model.comment;

import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.models.UserModel;

import java.util.UUID;

public record CommentResponse(
        UUID id,
        String comment,
        String date_at,
        PostModel post,
        UserModel user
) {
}
