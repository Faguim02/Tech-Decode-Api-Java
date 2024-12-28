package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.CommentDto;
import com.techdecode.blog.service.CommentService;
import com.techdecode.blog.view.model.comment.CommentRequest;
import com.techdecode.blog.view.model.comment.CommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Comentarios")
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    // Criar comentario
    @Operation(summary = "criar comentario", description = "nessa rota será criado um comentario que foi informado no body da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "comentario criado", content = @Content(schema = @Schema(implementation = CommentResponse.class))),
            @ApiResponse(responseCode = "403", description = "você não está authenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest body) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        CommentDto commentDto = new CommentDto(null, email, body.comment(), null, null, body.post());

        CommentDto commentDtoRes = this.commentService.createComment(commentDto, email);

        CommentResponse commentResponse = new CommentResponse(commentDtoRes.id(), commentDtoRes.comment(), commentDtoRes.date_at(), commentDtoRes.post(), commentDtoRes.user());

        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
    }


    // Deletar comentario
    @Operation(summary = "apagar comentario", description = "nessa rota, o usuario informa o id do comentario, e com base no seu jwt, será extarido de você é o criador do comentario, e por fim, será deletado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "comentario deletado", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "comentario do id inserido não existe", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("{id}")
    ResponseEntity<String> deleteComment(@PathVariable("id") UUID comment_id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        String message = this.commentService.deleteComment(email, comment_id);

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
