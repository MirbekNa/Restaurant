package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCategory.CategoryRequest;
import peaksoft.dto.dtoCategory.CategoryResponse;
import peaksoft.exceptions.BadRequestException;
import peaksoft.service.CategoryService;

import java.util.List;
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryAPI {
    private final CategoryService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAll")
    public List<CategoryResponse> getAllCategories() {
        return service.getAllCategories();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public SimpleResponse saveCategory(@RequestBody CategoryRequest categoryRequest) {
        return service.saveCategory(categoryRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public SimpleResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return service.updateCategory(id, categoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getById/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @DeleteMapping("/delete/{id}")
    public SimpleResponse deleteCategory(@PathVariable Long id) throws BadRequestException {
        return service.deleteCategoryById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/searchByName")
    public List<CategoryResponse> searchCategoryByName(@RequestParam String word) {
        return service.searchCategoryByName(word);
    }
}
