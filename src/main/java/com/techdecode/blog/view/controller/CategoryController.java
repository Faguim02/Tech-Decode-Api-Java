package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.CategoryDto;
import com.techdecode.blog.models.error.ErrorMessage;
import com.techdecode.blog.service.CategoryService;
import com.techdecode.blog.view.model.category.CategoryRequest;
import com.techdecode.blog.view.model.category.CategorysResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Categoria")
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Mostrar todas as categorias
    @Operation(summary = "mostrar todas as categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias foram encontradas", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CategorysResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Nenhuma categoria encontrada", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping
    ResponseEntity<List<CategorysResponse>> findAllCategory() {
        List<CategoryDto> categoryDtos = this.categoryService.findAllCategory();

        List<CategorysResponse> categoryResponses = categoryDtos.stream()
                .map(categoryDto -> new CategorysResponse(categoryDto.id(), categoryDto.title()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(categoryResponses);
    }


    // Criar uma nova categoria
    @Operation(summary = "criar categoria", description = "essa rota será criado uma categoria com os dados infromados no body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "categoria criada com sucesso", content = @Content(schema = @Schema(implementation = CategorysResponse.class))),
            @ApiResponse(responseCode = "400", description = "nome da categoria não informado", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "essa categoria já existe", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    ResponseEntity<CategorysResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryDto categoryDto = new CategoryDto(null, categoryRequest.title(), null);
        CategoryDto categoryDtoRes = this.categoryService.createCategory(categoryDto);

        CategorysResponse categorysResponse = new CategorysResponse(categoryDtoRes.id(), categoryDtoRes.title());

        return ResponseEntity.status(HttpStatus.CREATED).body(categorysResponse);
    }


    // Deletear uma categoria
    @Operation(summary = "deletar categoria", description = "nessa rota será deletado a categoria pelo id informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deletado com sucesso", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "a categoria do id fornecido não existe", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("{id}")
    ResponseEntity<String> deleteCategory(@PathVariable("id") UUID id) {
        String messageDeleted = this.categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(messageDeleted);
    }

}
