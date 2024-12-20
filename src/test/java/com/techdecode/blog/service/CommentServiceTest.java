package com.techdecode.blog.service;

import com.techdecode.blog.dto.CommentDto;
import com.techdecode.blog.models.CommentModel;
import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.models.UserModel;
import com.techdecode.blog.repository.CommentRepository;
import com.techdecode.blog.repository.PostRepository;
import com.techdecode.blog.repository.UserRepository;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

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

        @DisplayName("should return CommentDto")
        @Test
        void shouldReturnCommentDto() {
            UUID user_id = UUID.randomUUID();
            UUID post_id = UUID.randomUUID();

            UserModel userModel = new UserModel();
            PostModel postModel = new PostModel();
            CommentModel commentModel = new CommentModel();
            CommentDto commentDto = new CommentDto(UUID.randomUUID(), "title", "19 dez 2024", userModel, postModel);

            Mockito.when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(userModel));
            Mockito.when(postRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(postModel));
            Mockito.when(commentRepository.save(Mockito.any(CommentModel.class))).thenReturn(commentModel);

            CommentDto commentDtoRes = commentService.createComment(commentDto, user_id, post_id);

            Assertions.assertNotNull(commentDtoRes);
        }

    }

    @Nested
    @DisplayName("test method: deleteComment")
    class DeleteComment {}
}
