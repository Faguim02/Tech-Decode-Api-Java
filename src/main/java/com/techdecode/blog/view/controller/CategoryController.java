package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.CategoryDto;
import com.techdecode.blog.service.CategoryService;
import com.techdecode.blog.view.model.category.CategoryRequest;
import com.techdecode.blog.view.model.category.CategorysResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "mostrar todas as categorias")
    @GetMapping
    ResponseEntity<List<CategorysResponse>> findAllCategory() {
        List<CategoryDto> categoryDtos = this.categoryService.findAllCategory();

        List<CategorysResponse> categoryResponses = categoryDtos.stream()
                .map(categoryDto -> new CategorysResponse(categoryDto.id(), categoryDto.title()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(categoryResponses);
    }

    @Operation(summary = "criar categoria", description = "essa rota será criado uma categoria com os dados infromados no body")
    @PostMapping
    ResponseEntity<CategorysResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryDto categoryDto = new CategoryDto(null, categoryRequest.title(), null);
        CategoryDto categoryDtoRes = this.categoryService.createCategory(categoryDto);

        CategorysResponse categorysResponse = new CategorysResponse(categoryDtoRes.id(), categoryDtoRes.title());

        return ResponseEntity.status(HttpStatus.CREATED).body(categorysResponse);
    }

    @Operation(summary = "deletar categoria", description = "nessa rota será deletado a categoria pelo id informado")
    @DeleteMapping("{id}")
    ResponseEntity<String> deleteCategory(@PathVariable("id") UUID id) {
        String messageDeleted = this.categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(messageDeleted);
    }

}
