package com.techdecode.blog.service;

import com.techdecode.blog.dto.CategoryDto;
import com.techdecode.blog.models.CategoryModel;
import com.techdecode.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> findAllCategory() {
        List<CategoryModel> categorys = categoryRepository.findAll();

        List<CategoryDto> categoryDtos = categorys
                .stream()
                .map(categoryModel -> new CategoryDto(categoryModel.getId(), categoryModel.getTitle(), null))
                .toList();

        if (categoryDtos.size() == 0) {
            // todo exception
        }

        return categoryDtos;
    }
}
