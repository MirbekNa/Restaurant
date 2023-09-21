package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCategory.CategoryRequest;
import peaksoft.dto.dtoCategory.CategoryResponse;
import peaksoft.dto.dtoCategory.PaginationResponse;
import peaksoft.exceptions.BadRequestException;

public interface CategoryService {
    PaginationResponse getAllCategories(int currentPage,int pageSize);
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest);
    CategoryResponse getCategoryById(Long id);
    SimpleResponse deleteCategoryById(Long id) throws BadRequestException;
    PaginationResponse searchCategoryByName(String word, int currentPage, int pageSize);

}
