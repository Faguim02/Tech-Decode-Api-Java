package com.techdecode.blog.service;

import com.techdecode.blog.dto.CategoryDto;
import com.techdecode.blog.dto.PostDto;
import com.techdecode.blog.models.CategoryModel;
import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.models.exceptions.ConflictException;
import com.techdecode.blog.models.exceptions.NotFoundException;
import com.techdecode.blog.repository.CategoryRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DisplayName("test: post service")
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @Nested
    @DisplayName("test method: createPost")
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

    @Nested
    @DisplayName("test method: findAllPosts")
    class FindAllPost {

        @DisplayName("should return all posts")
        @Test
        void shouldReturnAllPosts() {

            // data
            UUID id = UUID.randomUUID();
            PostModel postModel= new PostModel();
            postModel.setId(id);
            postModel.setTitle("postagem");
            postModel.setBannerUrl("link");
            postModel.setDescription("aaa aaa");

            List<PostModel> postModels = List.of(postModel);

            // mock
            Mockito.when(postRepository.findAll()).thenReturn(postModels);

            List<PostDto> postDtos = postService.findAllPost();

            Assertions.assertNotNull(postDtos);
            Assertions.assertEquals(1, postDtos.size());
            Assertions.assertEquals("postagem", postDtos.get(0).title());
        }

        @DisplayName("should return not found exception")
        @Test
        void shouldReturnNotFoundException() {

            // data
            List<PostModel> postModels = List.of();

            // mock
            Mockito.when(postRepository.findAll()).thenReturn(postModels);

            Assertions.assertThrows(NotFoundException.class, () -> postService.findAllPost());
        }

    }

    @Nested
    @DisplayName("test method: findPostById")
    class FindPostById {

        @DisplayName("should return post")
        @Test
        void shouldReturnPost() {
            // data
            UUID id = UUID.randomUUID();
            PostModel postModel= new PostModel();
            postModel.setId(id);
            postModel.setTitle("postagem");
            postModel.setBannerUrl("link");
            postModel.setDescription("aaa aaa");

            // mock
            Mockito.when(postRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(postModel));

            PostDto postDto = postService.findPostById(id);

            // result
            Assertions.assertNotNull(postDto);
            Assertions.assertEquals("postagem", postDto.title());
        }

        @DisplayName("should return not found exception")
        @Test
        void shoulReturnNotFoundException() {
            // data
            UUID id = UUID.randomUUID();

            // mock
            Mockito.when(postRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

            Assertions.assertThrows(NotFoundException.class, () -> postService.findPostById(id));
        }

    }

    @Nested
    @DisplayName("test method: findPostByCategory")
    class FindByCategory{
        @DisplayName("should return posts for category")
        @Test
        void shouldReturnPostByCategory() {
            // data
            PostModel postModel= new PostModel();
            postModel.setId(UUID.randomUUID());
            postModel.setTitle("postagem");
            postModel.setBannerUrl("link");
            postModel.setDescription("aaa aaa");

            UUID id = UUID.randomUUID();
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setId(id);
            categoryModel.setTitle("ia");
            categoryModel.setPostModels(List.of(postModel));

            // mock
            Mockito.when(categoryRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(categoryModel));
            CategoryDto categoryDto = postService.findPostByCategory(id);

            // result
            Assertions.assertEquals(1, categoryDto.postModels().size());
        }

        @DisplayName("should return not found exception")
        @Test
        void shouldReturnNotFoundException() {
            Mockito.when(categoryRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

            Assertions.assertThrows(NotFoundException.class, () -> postService.findPostByCategory(UUID.randomUUID()));
        }
    }

    @Nested
    @DisplayName("test method: updatePost")
    class UpdatePost{
        @DisplayName("should update and return post")
        @Test
        void shouldUpdateAndReturnPost() {
            // data
            UUID id = UUID.randomUUID();
            PostModel postModel= new PostModel();
            postModel.setId(id);
            postModel.setTitle("postagem");
            postModel.setBannerUrl("link");
            postModel.setDescription("aaa aaa");

            PostDto postDto = new PostDto(id, postModel.getTitle(), postModel.getBannerUrl(), postModel.getDescription(), postModel.getFont(), postModel.getDate_at(), null, null);

            // mock
            Mockito.when(postRepository.save(Mockito.any(PostModel.class))).thenReturn(postModel);
            Mockito.when(postRepository.existsById(Mockito.any(UUID.class))).thenReturn(true);

            PostDto postDtoRes = postService.updatePost(id, postDto);

            Assertions.assertNotNull(postDtoRes);

        }

        @DisplayName("should return conflict exception")
        @Test
        void shouldReturnConflictException() {
            // data
            UUID id = UUID.randomUUID();
            PostModel postModel= new PostModel();
            postModel.setId(id);
            postModel.setTitle("postagem");
            postModel.setBannerUrl("link");
            postModel.setDescription("aaa aaa");

            PostDto postDto = new PostDto(id, postModel.getTitle(), postModel.getBannerUrl(), postModel.getDescription(), postModel.getFont(), postModel.getDate_at(), null, null);

            // mock
            Mockito.when(postRepository.findByTitle(Mockito.any(String.class))).thenReturn(postModel);
            Mockito.when(postRepository.existsById(Mockito.any(UUID.class))).thenReturn(true);

            Assertions.assertThrows(ConflictException.class, () -> postService.updatePost(id, postDto));
        }

        @DisplayName("should return not found exception")
        @Test
        void shouldReturnNotFoundException() {
            // data
            UUID id = UUID.randomUUID();
            PostModel postModel= new PostModel();
            postModel.setId(id);
            postModel.setTitle("postagem");
            postModel.setBannerUrl("link");
            postModel.setDescription("aaa aaa");

            PostDto postDto = new PostDto(id, postModel.getTitle(), postModel.getBannerUrl(), postModel.getDescription(), postModel.getFont(), postModel.getDate_at(), null, null);

            // mock
            Mockito.when(postRepository.existsById(Mockito.any(UUID.class))).thenReturn(false);

            Assertions.assertThrows(NotFoundException.class, () -> postService.updatePost(id, postDto));
        }

    }

    @Nested
    @DisplayName("test method: deletePost")
    class DeletePost{
        @DisplayName("should return message 'Postagem deletada'")
        @Test
        void shouldReturnMessage() {

            Mockito.when(postRepository.existsById(Mockito.any(UUID.class))).thenReturn(true);

            String postDeleted = postService.deletePost(UUID.randomUUID());

            Assertions.assertEquals("Postagem deletada", postDeleted);
        }

        @DisplayName("should return not found exception")
        @Test
        void shouldReturnNotFoundException() {

            Mockito.when(postRepository.existsById(Mockito.any(UUID.class))).thenReturn(false);

            Assertions.assertThrows(NotFoundException.class, () -> postService.deletePost(UUID.randomUUID()));
        }
    }

    @Nested
    @DisplayName("test method: searchPost")
    class SearchPost {

        @DisplayName("should return posts")
        @Test
        void shouldReturnPost() {
            // data
            UUID id = UUID.randomUUID();
            PostModel postModel= new PostModel();
            postModel.setId(id);
            postModel.setTitle("postagem");
            postModel.setBannerUrl("link");
            postModel.setDescription("aaa aaa");

            List<PostModel> postModels = List.of(postModel);

            // mock
            Mockito.when(postRepository.findByTitleContaining(Mockito.any(String.class))).thenReturn(postModels);

            List<PostDto> postDtos = postService.searchPost("postagem");
            // result
            Assertions.assertEquals(1, postDtos.size());
        }
    }
}
