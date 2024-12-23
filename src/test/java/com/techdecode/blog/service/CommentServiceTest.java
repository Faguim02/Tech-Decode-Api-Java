package com.techdecode.blog.service;

import com.techdecode.blog.dto.CommentDto;
import com.techdecode.blog.models.CommentModel;
import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.models.UserModel;
import com.techdecode.blog.models.exceptions.ForbiddenException;
import com.techdecode.blog.models.exceptions.NotFoundException;
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
            CommentDto commentDto = new CommentDto(UUID.randomUUID(), "fagner", "title", "19 dez 2024", userModel, postModel);

            Mockito.when(userRepository.findByEmail(Mockito.any(String.class))).thenReturn(userModel);
            Mockito.when(commentRepository.save(Mockito.any(CommentModel.class))).thenReturn(commentModel);

            CommentDto commentDtoRes = commentService.createComment(commentDto, "fagner@");

            Assertions.assertNotNull(commentDtoRes);
        }

        @DisplayName("should return forbiddenException")
        @Test
        void shouldReturnForbiddenException() {
            UUID user_id = UUID.randomUUID();
            UUID post_id = UUID.randomUUID();

            UserModel userModel = new UserModel();
            PostModel postModel = new PostModel();
            CommentDto commentDto = new CommentDto(UUID.randomUUID(), "fagner", "title", "19 dez 2024", userModel, postModel);

            Assertions.assertThrows(ForbiddenException.class, () -> commentService.createComment(commentDto, "fagner@"));
        }

    }

    @Nested
    @DisplayName("test method: deleteComment")
    class DeleteComment {
        @DisplayName("should return message 'comentario deletado'")
        @Test
        void shouldReturnMessage() {
            UUID user_id = UUID.randomUUID();
            UUID comment_id = UUID.randomUUID();

            CommentModel commentModel = new CommentModel();
            UserModel userModel = new UserModel();
            userModel.setId(user_id);
            commentModel.setUser(userModel);

            Mockito.when(commentRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(commentModel));

            String message = commentService.deleteComment("fagner@", comment_id);

            Assertions.assertEquals("comentario deletado", message);
        }

        @DisplayName("should return not found exception")
        @Test
        void shouldReturnNotFoundException() {
            UUID user_id = UUID.randomUUID();
            UUID comment_id = UUID.randomUUID();

            Mockito.when(commentRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

            Assertions.assertThrows(NotFoundException.class, () -> commentService.deleteComment("fagner@", comment_id));
        }

        @DisplayName("should return forbidden exception")
        @Test
        void shouldReturnForbiddenException() {
            UUID user_id = UUID.randomUUID();
            UUID comment_id = UUID.randomUUID();

            CommentModel commentModel = new CommentModel();
            UserModel userModel = new UserModel();
            userModel.setId(UUID.randomUUID());
            commentModel.setUser(userModel);

            Mockito.when(commentRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(commentModel));

            Assertions.assertThrows(ForbiddenException.class, () -> commentService.deleteComment("fagner@", user_id));
        }
    }
}
