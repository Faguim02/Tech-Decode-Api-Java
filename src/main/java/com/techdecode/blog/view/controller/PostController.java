package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.CategoryDto;
import com.techdecode.blog.dto.PostDto;
import com.techdecode.blog.service.PostService;
import com.techdecode.blog.view.model.post.PostCreateRequest;
import com.techdecode.blog.view.model.post.PostCreateResponse;
import com.techdecode.blog.view.model.post.PostResponse;
import com.techdecode.blog.view.model.post.PostResponseDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    ResponseEntity<PostCreateResponse> createPost(@RequestBody @Valid PostCreateRequest createRequest) {
        PostDto postDto = new PostDto(null, createRequest.title(), createRequest.bannerUrl(), createRequest.description(), createRequest.font(), null, null, createRequest.category());
        PostDto postDtoRes = this.postService.createPost(postDto);

        PostCreateResponse postCreateResponse = new PostCreateResponse(postDtoRes.id(), postDtoRes.title(), postDtoRes.bannerUrl(), postDtoRes.description(), postDtoRes.font(), postDtoRes.data_at());

        return ResponseEntity.status(HttpStatus.CREATED).body(postCreateResponse);
    }

    @GetMapping
    ResponseEntity<List<PostResponse>> findAllPost() {
        List<PostDto> postDtos = this.postService.findAllPost();
        List<PostResponse> postResponses = postDtos.stream()
                .map(postDto -> new PostResponse(postDto.id(), postDto.title(), postDto.bannerUrl(), postDto.data_at()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(postResponses);
    }

    @GetMapping("{id}")
    ResponseEntity<PostResponseDetails> findPostById(@PathVariable("id") UUID id) {
        PostDto postDto = this.postService.findPostById(id);
        PostResponseDetails postResponseDetails = new PostResponseDetails(postDto.id(), postDto.title(), postDto.bannerUrl(), postDto.description(), postDto.font(), postDto.data_at(), postDto.comments(), null);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDetails);
    }

    @GetMapping("search/{search}")
    ResponseEntity<List<PostResponse>> searchPost(@PathVariable("search") String search) {
        List<PostDto> postDtos = this.postService.searchPost(search);
        List<PostResponse> postResponses = postDtos.stream()
                .map(postDto -> new PostResponse(postDto.id(), postDto.title(), postDto.bannerUrl(), postDto.data_at()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(postResponses);
    }

    @GetMapping("category/{id}")
    ResponseEntity<CategoryDto> findPostByCategory(@PathVariable("id") UUID id) {
        CategoryDto categoryDto = this.postService.findPostByCategory(id);

        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    @PutMapping("{id}")
    ResponseEntity<PostResponse> updatePost(@PathVariable("id") UUID id, @RequestBody PostDto postDto) {
        PostDto postDtoRes = this.postService.updatePost(id, postDto);
        PostResponse postResponse = new PostResponse(postDtoRes.id(), postDtoRes.title(), postDtoRes.bannerUrl(), postDtoRes.data_at());

        return ResponseEntity.status(HttpStatus.OK).body(postResponse);
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> update(@PathVariable("id") UUID id) {
        String message = this.postService.deletePost(id);

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
