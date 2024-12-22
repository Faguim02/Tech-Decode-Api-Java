package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.CategoryDto;
import com.techdecode.blog.service.CategoryService;
import com.techdecode.blog.view.model.category.CategorysResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


}
