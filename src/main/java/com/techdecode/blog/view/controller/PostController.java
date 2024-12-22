package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.PostDto;
import com.techdecode.blog.service.PostService;
import com.techdecode.blog.view.model.post.PostCreateRequest;
import com.techdecode.blog.view.model.post.PostCreateResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
