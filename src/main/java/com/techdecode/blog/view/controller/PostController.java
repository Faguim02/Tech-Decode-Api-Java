package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.CategoryDto;
import com.techdecode.blog.dto.PostDto;
import com.techdecode.blog.models.CategoryModel;
import com.techdecode.blog.service.PostService;
import com.techdecode.blog.view.model.post.PostCreateRequest;
import com.techdecode.blog.view.model.post.PostCreateResponse;
import com.techdecode.blog.view.model.post.PostResponse;
import com.techdecode.blog.view.model.post.PostResponseDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("post")
@Tag(name = "Post", description = "rotas das postagens do Techdecode")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "criar postagem", description = "essa rota cria uma nova postagem ao blog")
    @PostMapping(consumes = {"multipart/form-data"})
    ResponseEntity<Object> createPost(
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart("font") String font,
            @RequestPart("category_id") String category_id
    ) throws UnsupportedEncodingException {

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(UUID.fromString(formatUTF8(category_id)));
        PostDto postDto = new PostDto(null, formatUTF8(title), null, formatUTF8(description), formatUTF8(font), null, null, categoryModel);

        PostDto postDtoRes = this.postService.createPost(postDto, photo);

        PostCreateResponse postCreateResponse = new PostCreateResponse(postDtoRes.id(), postDtoRes.title(), postDtoRes.bannerUrl(), postDtoRes.description(), postDtoRes.font(), postDtoRes.data_at());

        return ResponseEntity.status(HttpStatus.CREATED).body(new String(title.getBytes("ISO-8859-1"), "UTF-8"));
    }

    @Operation(summary = "retornar todas as postagens", description = "essa rota retorna todas postagens do blog")
    @GetMapping
    ResponseEntity<List<PostResponse>> findAllPost() {
        List<PostDto> postDtos = this.postService.findAllPost();
        List<PostResponse> postResponses = postDtos.stream()
                .map(postDto -> new PostResponse(postDto.id(), postDto.title(), postDto.bannerUrl(), postDto.data_at()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(postResponses);
    }

    @Operation(summary = "retornar postagem por id", description = "essa rota retorna somente uma postagem do blog pelo id")
    @GetMapping("{id}")
    ResponseEntity<PostResponseDetails> findPostById(@PathVariable("id") UUID id) {
        PostDto postDto = this.postService.findPostById(id);
        PostResponseDetails postResponseDetails = new PostResponseDetails(postDto.id(), postDto.title(), postDto.bannerUrl(), postDto.description(), postDto.font(), postDto.data_at(), postDto.comments(), null);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDetails);
    }

    @Operation(summary = "pesquisar postagens", description = "essa rota busca por postagens que contenha o texto informado no titulo")
    @GetMapping("search/{search}")
    ResponseEntity<List<PostResponse>> searchPost(@PathVariable("search") String search) {
        List<PostDto> postDtos = this.postService.searchPost(search);
        List<PostResponse> postResponses = postDtos.stream()
                .map(postDto -> new PostResponse(postDto.id(), postDto.title(), postDto.bannerUrl(), postDto.data_at()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(postResponses);
    }

    @Operation(summary = "retornar postagem por categoria", description = "nessa rota é passado o id da categoria, e por fim é rotornado a categoria com suas postagens")
    @GetMapping("category/{id}")
    ResponseEntity<CategoryDto> findPostByCategory(@PathVariable("id") UUID id) {
        CategoryDto categoryDto = this.postService.findPostByCategory(id);

        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    @Operation(summary = "editar postagem", description = "nessa rota é informado o id do post que deseja editar e um objrto no body com os dados que deseja alterar")
    @PutMapping("{id}")
    ResponseEntity<PostResponse> updatePost(@PathVariable("id") UUID id, @RequestBody PostDto postDto) {
        PostDto postDtoRes = this.postService.updatePost(id, postDto);
        PostResponse postResponse = new PostResponse(postDtoRes.id(), postDtoRes.title(), postDtoRes.bannerUrl(), postDtoRes.data_at());

        return ResponseEntity.status(HttpStatus.OK).body(postResponse);
    }

    @Operation(summary = "deletar postagem", description = "nessa rota sera informado o id do post que deseja apagar")
    @DeleteMapping("{id}")
    ResponseEntity<String> update(@PathVariable("id") UUID id) {
        String message = this.postService.deletePost(id);

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    private String formatUTF8(String encodingTxt) throws UnsupportedEncodingException {
        return new String(encodingTxt.getBytes("ISO-8859-1"));
    }

}
