package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.CategoryDto;
import com.techdecode.blog.service.CategoryService;
import com.techdecode.blog.view.model.category.CategoryRequest;
import com.techdecode.blog.view.model.category.CategorysResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    ResponseEntity<List<CategorysResponse>> findAllCategory() {
        List<CategoryDto> categoryDtos = this.categoryService.findAllCategory();

        List<CategorysResponse> categoryResponses = categoryDtos.stream()
                .map(categoryDto -> new CategorysResponse(categoryDto.id(), categoryDto.title()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(categoryResponses);
    }

    @PostMapping
    ResponseEntity<CategorysResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryDto categoryDto = new CategoryDto(null, categoryRequest.title(), null);
        CategoryDto categoryDtoRes = this.categoryService.createCategory(categoryDto);

        CategorysResponse categorysResponse = new CategorysResponse(categoryDtoRes.id(), categoryDtoRes.title());

        return ResponseEntity.status(HttpStatus.CREATED).body(categorysResponse);
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> deleteCategory(@PathVariable("id") UUID id) {
        String messageDeleted = this.categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(messageDeleted);
    }

}
