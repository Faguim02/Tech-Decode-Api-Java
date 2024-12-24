package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.CommentDto;
import com.techdecode.blog.service.CommentService;
import com.techdecode.blog.view.model.comment.CommentRequest;
import com.techdecode.blog.view.model.comment.CommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Comentarios")
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "criar comentario", description = "nessa rota será criado um comentario que foi informado no body da requisição")
    @PostMapping
    ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest body) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        CommentDto commentDto = new CommentDto(null, email, body.comment(), null, null, body.post());

        CommentDto commentDtoRes = this.commentService.createComment(commentDto, email);

        CommentResponse commentResponse = new CommentResponse(commentDtoRes.id(), commentDtoRes.comment(), commentDtoRes.date_at(), commentDtoRes.post(), commentDtoRes.user());

        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
    }

    @Operation(summary = "apagar comentario", description = "nessa rota, o usuario informa o id do comentario, e com base no seu jwt, será extarido de você é o criador do comentario, e por fim, será deletado")
    @DeleteMapping("{id}")
    ResponseEntity<String> deleteComment(@PathVariable("id") UUID comment_id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        String message = this.commentService.deleteComment(email, comment_id);

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
