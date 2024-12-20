package com.techdecode.blog.dto;

import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.models.UserModel;

import java.util.UUID;

public record CommentDto(
        UUID id,
        String comment,
        String date_at,
        UserModel user,
        PostModel post
) {
}
