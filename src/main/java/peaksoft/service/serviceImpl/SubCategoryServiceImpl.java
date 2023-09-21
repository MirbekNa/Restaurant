package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoSubcategory.PaginationSubCategoryResponse;
import peaksoft.dto.dtoSubcategory.SubCategoryRequest;
import peaksoft.dto.dtoSubcategory.SubCategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.exceptions.NoSuchElementException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.SubCategoryService;


@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository repository;
    private final CategoryRepository categoryRepository;

    @Override
    public PaginationSubCategoryResponse getAllSubCategory(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<SubCategoryResponse> allSubCategories = repository.getAllSubCategories(pageable);
        return PaginationSubCategoryResponse.builder()
                .subCategoryResponses(allSubCategories.getContent())
                .currentPage(allSubCategories.getNumber()+1)
                .pageSize(allSubCategories.getTotalPages())
                .build();
    }

    @Override
    public SimpleResponse saveSubCategory(Long categoryId, SubCategoryRequest subCategoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException(String.format("Category with id:%s is not found", categoryId)));
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryRequest.name());
        subCategory.setCategory(category);
        repository.save(subCategory);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("SubCategory with id:%s successfully saved...",subCategory.getId()))
                .build();
    }

    @Override
    public SimpleResponse updateSubCategory(Long id, SubCategoryRequest subCategoryRequest) {
        SubCategory subCategory = repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("SubCategory with id:%s not found", id)));
        subCategory.setName(subCategoryRequest.name());
        repository.save(subCategory);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated...")
                .build();
    }

    @Override
    public SubCategoryResponse getSubCategoryById(Long id) {
        SubCategory subCategory = repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("SubCategory with id:%s not found", id)));

        return SubCategoryResponse.builder()
                .name(subCategory.getName())
                .build();
    }

    @Override
    public SimpleResponse deleteSubCategory(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
        }else throw new NoSuchElementException(String.format("SubCategory with id:%s not found", id));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted...")
                .build();
    }

    @Override
    public PaginationSubCategoryResponse filterSubCategoryByCategory(String categoryName, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<SubCategoryResponse> subCategories = repository.filterSubCategoryByCategoryName(categoryName, pageable);

        return PaginationSubCategoryResponse.builder()
                .subCategoryResponses(subCategories.getContent())
                .currentPage(subCategories.getNumber()+1)
                .pageSize(subCategories.getTotalPages())
                .build();
    }

    @Override
    public PaginationSubCategoryResponse getAllSubCategoryByGroup(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<SubCategoryResponse> subCategories = repository.getAllSubCategoryByGroup(pageable);
                return PaginationSubCategoryResponse.builder()
                        .subCategoryResponses(subCategories.getContent())
                        .currentPage(subCategories.getNumber()+1)
                        .pageSize(subCategories.getTotalPages())
                        .build();
    }

    @Override
    public PaginationSubCategoryResponse searchByName(String word, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<SubCategoryResponse> search = repository.search(word, pageable);
        return PaginationSubCategoryResponse.builder()
                .subCategoryResponses(search.getContent())
                .currentPage(search.getNumber()+1)
                .pageSize(search.getTotalPages())
                .build();
    }
}
