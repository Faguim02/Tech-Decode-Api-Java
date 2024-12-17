package com.techdecode.blog.service;

import com.techdecode.blog.dto.PostDto;
import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.models.exceptions.ConflictException;
import com.techdecode.blog.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.UUID;

@DisplayName("test: post service")
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;

    @Nested
    @DisplayName("test method: create post")
    class CreatePost {

        @DisplayName("should create post and return data")
        @Test
        void shouldCreatePost() {

            UUID id = UUID.randomUUID();
            PostModel postModel= new PostModel();
            postModel.setId(id);
            postModel.setTitle("postagem");
            postModel.setBannerUrl("link");
            postModel.setDescription("aaa aaa");

            PostDto postDto = new PostDto(id, postModel.getTitle(), postModel.getBannerUrl(), postModel.getDescription(), postModel.getFont(), postModel.getDate_at(), null, null);

            Mockito.when(postRepository.save(Mockito.any(PostModel.class))).thenReturn(postModel);
            PostDto postDtoRes = postService.createPost(postDto);

            Assertions.assertNotNull(postDtoRes);

        }

        @DisplayName("should return conflict exception")
        @Test
        void shouldReturnConflictException() {

            PostModel postModel = new PostModel();
            postModel.setTitle("psotagem");
            PostDto postDto = new PostDto(UUID.randomUUID(), postModel.getTitle(), postModel.getBannerUrl(), postModel.getDescription(), postModel.getFont(), postModel.getDate_at(), null, null);

            Mockito.when(postRepository.findByTitle(Mockito.any(String.class))).thenReturn(postModel);

            Assertions.assertThrows(ConflictException.class, () -> postService.createPost(postDto));

        }

    }

}
