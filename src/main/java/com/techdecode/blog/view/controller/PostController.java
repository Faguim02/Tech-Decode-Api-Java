package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.CategoryDto;
import com.techdecode.blog.dto.PostDto;
import com.techdecode.blog.models.CategoryModel;
import com.techdecode.blog.service.PostService;
import com.techdecode.blog.view.model.post.PostCreateResponse;
import com.techdecode.blog.view.model.post.PostResponse;
import com.techdecode.blog.view.model.post.PostResponseDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("post")
@Tag(name = "Post", description = "rotas das postagens do Techdecode")
public class PostController {

    @Autowired
    private PostService postService;


    // Criar postagem
    @Operation(summary = "criar postagem", description = "essa rota cria uma nova postagem ao blog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "postagem criada", content = @Content(schema = @Schema(implementation = PostCreateResponse.class))),
            @ApiResponse(responseCode = "409", description = "já existe uma postagem com esse titulo", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(consumes = {"multipart/form-data"})
    ResponseEntity<PostCreateResponse> createPost(
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart("font") String font,
            @RequestPart("category_id") String category_id
    ) throws IOException {

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(UUID.fromString(formatUTF8(category_id)));
        PostDto postDto = new PostDto(null, formatUTF8(title), null, formatUTF8(description), formatUTF8(font), null, null, categoryModel);

        PostDto postDtoRes = this.postService.createPost(postDto, photo);

        PostCreateResponse postCreateResponse = new PostCreateResponse(postDtoRes.id(), postDtoRes.title(), postDtoRes.bannerUrl(), postDtoRes.description(), postDtoRes.font(), postDtoRes.data_at());

        return ResponseEntity.status(HttpStatus.CREATED).body(postCreateResponse);
    }


    /// Retornar todas as noticias
    @Operation(summary = "retornar todas as postagens", description = "essa rota retorna todas postagens do blog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "postagens encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponse.class)))),
            @ApiResponse(responseCode = "404", description = "nenhuma postagem encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    ResponseEntity<List<PostResponse>> findAllPost() {
        List<PostDto> postDtos = this.postService.findAllPost();
        List<PostResponse> postResponses = postDtos.stream()
                .map(postDto -> new PostResponse(postDto.id(), postDto.title(), postDto.bannerUrl(), postDto.data_at()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(postResponses);
    }


    // Retornar noticia por id
    @Operation(summary = "retornar postagem por id", description = "essa rota retorna somente uma postagem do blog pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "postagem encontrada", content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "404", description = "nenhuma postagem encontrada com esse id inserido", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("{id}")
    ResponseEntity<PostResponseDetails> findPostById(@PathVariable("id") UUID id) {
        PostDto postDto = this.postService.findPostById(id);
        PostResponseDetails postResponseDetails = new PostResponseDetails(postDto.id(), postDto.title(), postDto.bannerUrl(), postDto.description(), postDto.font(), postDto.data_at(), postDto.comments(), null);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDetails);
    }


    // Procurar por noticia
    @Operation(summary = "pesquisar postagens", description = "essa rota busca por postagens que contenha o texto informado no titulo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "postagens encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponse.class)))),
    })
    @GetMapping("search/{search}")
    ResponseEntity<List<PostResponse>> searchPost(@PathVariable("search") String search) {
        List<PostDto> postDtos = this.postService.searchPost(search);
        List<PostResponse> postResponses = postDtos.stream()
                .map(postDto -> new PostResponse(postDto.id(), postDto.title(), postDto.bannerUrl(), postDto.data_at()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(postResponses);
    }


    // Buscar por categoria
    @Operation(summary = "retornar postagem por categoria", description = "nessa rota é passado o id da categoria, e por fim é rotornado a categoria com suas postagens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "postagens encontradas pela categoria", content = @Content(schema = @Schema(implementation = CategoryDto.class))),
            @ApiResponse(responseCode = "404", description = "nenhuma postagem encontrada com esse id inserido", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("category/{id}")
    ResponseEntity<CategoryDto> findPostByCategory(@PathVariable("id") UUID id) {
        CategoryDto categoryDto = this.postService.findPostByCategory(id);

        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }


    // Editar noticia
    @Operation(summary = "editar postagem", description = "nessa rota é informado o id do post que deseja editar e um objrto no body com os dados que deseja alterar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "postagem editada", content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "404", description = "postagem do id inserido não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "já existe uma postagem com esse titulo", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("{id}")
    ResponseEntity<PostResponse> updatePost(
            @PathVariable("id") UUID id,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart("font") String font,
            @RequestPart("category_id") String category_id
    ) throws IOException {

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(UUID.fromString(formatUTF8(category_id)));
        PostDto postDto = new PostDto(null, formatUTF8(title), null, formatUTF8(description), formatUTF8(font), null, null, categoryModel);

        PostDto postDtoRes = this.postService.updatePost(id, postDto, photo);

        PostResponse postResponse = new PostResponse(postDtoRes.id(), postDtoRes.title(), postDtoRes.bannerUrl(), postDtoRes.data_at());

        return ResponseEntity.status(HttpStatus.OK).body(postResponse);
    }


    // Deletar noticia
    @Operation(summary = "deletar postagem", description = "nessa rota sera informado o id do post que deseja apagar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deletado", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "postagem do id inserido não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("{id}")
    ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        String message = this.postService.deletePost(id);

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    private String formatUTF8(String encodingTxt) throws UnsupportedEncodingException {
        return new String(encodingTxt.getBytes("ISO-8859-1"));
    }

}
