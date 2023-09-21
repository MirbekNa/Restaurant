package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoSubcategory.PaginationSubCategoryResponse;
import peaksoft.dto.dtoSubcategory.SubCategoryRequest;
import peaksoft.dto.dtoSubcategory.SubCategoryResponse;

public interface SubCategoryService {
    PaginationSubCategoryResponse getAllSubCategory(int currentPage, int pageSize);
    SimpleResponse saveSubCategory(Long categoryId,SubCategoryRequest subCategoryRequest);
    SimpleResponse updateSubCategory(Long id,SubCategoryRequest subCategoryRequest);
    SubCategoryResponse getSubCategoryById(Long id);
    SimpleResponse deleteSubCategory(Long id);
    PaginationSubCategoryResponse filterSubCategoryByCategory(String categoryName, int currentPage, int pageSize);
    PaginationSubCategoryResponse getAllSubCategoryByGroup(int currentPage, int pageSize);
    PaginationSubCategoryResponse searchByName(String word,int currentPage, int pageSize);
}
