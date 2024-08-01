package com.techdecode.blog.controller;

import com.techdecode.blog.dtos.CommentDto;
import com.techdecode.blog.models.CommentModel;
import com.techdecode.blog.models.PostModel;
import com.techdecode.blog.repository.CommentRepository;
import com.techdecode.blog.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    @PostMapping("comment")
    ResponseEntity<Object> createComment(@RequestBody() @Valid CommentDto commentDto) {
        CommentModel commentModel = new CommentModel();

        Optional<PostModel> post0 = postRepository.findById(UUID.fromString(commentDto.post_id()));

        if(post0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not exist");
        }

        BeanUtils.copyProperties(commentDto, commentModel);
        commentModel.setPost(post0.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(commentRepository.save(commentModel));
    }
}
