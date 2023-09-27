package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoSubcategory.SubCategoryRequest;
import peaksoft.dto.dtoSubcategory.SubCategoryResponse;

import java.util.List;

public interface SubCategoryService {
    List<SubCategoryResponse> getAllSubCategory();
    SimpleResponse saveSubCategory(Long categoryId,SubCategoryRequest subCategoryRequest);
    SimpleResponse updateSubCategory(Long id,SubCategoryRequest subCategoryRequest);
    SubCategoryResponse getSubCategoryById(Long id);
    SimpleResponse deleteSubCategory(Long id);
    List<SubCategoryResponse> filterSubCategoryByCategory(String categoryName);
    List<SubCategoryResponse> searchByName(String word);
    List<SubCategoryResponse> getAllSubCategoryByGroup();

}
