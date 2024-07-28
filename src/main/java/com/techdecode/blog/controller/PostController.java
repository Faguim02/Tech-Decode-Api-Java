package com.techdecode.blog.controller;

import com.techdecode.blog.dtos.PostDto;
import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @PostMapping("post/")
    public ResponseEntity<PostModel> createPost(@RequestBody @Valid PostDto postDto) {
        var postModel = new PostModel();
        BeanUtils.copyProperties(postDto, postModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(postRepository.save(postModel));
    }


}
