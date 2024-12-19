package com.techdecode.blog.service;

import com.techdecode.blog.repository.CommentRepository;
import com.techdecode.blog.repository.PostRepository;
import com.techdecode.blog.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("test: comment service")
@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepository commentRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PostRepository postRepository;

    @Nested
    @DisplayName("test method: createComment")
    class CreateComment {

    }

    @Nested
    @DisplayName("test method: deleteComment")
    class DeleteComment {}
}
