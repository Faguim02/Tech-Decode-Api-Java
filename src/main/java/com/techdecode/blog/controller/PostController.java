package com.techdecode.blog.controller;

import com.techdecode.blog.dtos.PostDto;
import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @PostMapping("post/")
    public ResponseEntity<PostModel> createPost(@RequestBody @Valid PostDto postDto) {
        var postModel = new PostModel();

        BeanUtils.copyProperties(postDto, postModel);

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date_at = localDate.format(formatter);
        postModel.setDate_at(date_at);

        return ResponseEntity.status(HttpStatus.CREATED).body(postRepository.save(postModel));
    }

    @GetMapping("post/")
    public  ResponseEntity<List<PostModel>> findAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postRepository.findAll());
    }

}
