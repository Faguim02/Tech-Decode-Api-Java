package com.techdecode.blog.view.model.comment;

import com.techdecode.blog.models.PostModel;

public record CommentRequest(
        String comment,
        PostModel post
) {
}
