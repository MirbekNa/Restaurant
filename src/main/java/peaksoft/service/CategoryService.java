package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCategory.CategoryRequest;
import peaksoft.dto.dtoCategory.CategoryResponse;
import peaksoft.exceptions.BadRequestException;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest);
    CategoryResponse getCategoryById(Long id);
    SimpleResponse deleteCategoryById(Long id) throws BadRequestException;
    List<CategoryResponse> searchCategoryByName(String word);

}
