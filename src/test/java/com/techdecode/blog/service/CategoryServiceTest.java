package com.techdecode.blog.service;

import com.techdecode.blog.dto.CategoryDto;
import com.techdecode.blog.models.CategoryModel;
import com.techdecode.blog.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@DisplayName("test: category service")
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    // todo should if return all category
    @Nested
    @DisplayName("method test: findAllCategory")
    class FindAllCategory {

        @DisplayName("should show all categorys from data correct")
        @Test
        void shouldShowAllCategory() {

            //data
            CategoryModel categoryModel = new CategoryModel();
            UUID id = UUID.randomUUID();
            categoryModel.setId(id);
            categoryModel.setTitle("Comentario");
            categoryModel.setPostModels(null);

            List<CategoryModel> categoryModels = List.of(categoryModel);

            //mock
            Mockito.when(categoryRepository.findAll()).thenReturn(categoryModels);
            List<CategoryDto> categoryDtos = categoryService.findAllCategory();

            //result
            Assertions.assertEquals(1, categoryDtos.size());
            Assertions.assertEquals(id.toString(), categoryDtos.get(0).id().toString());
            Assertions.assertEquals("Comentario", categoryDtos.get(0).name());
            Assertions.assertNull(categoryDtos.get(0).postModels());

        }

    }
}
