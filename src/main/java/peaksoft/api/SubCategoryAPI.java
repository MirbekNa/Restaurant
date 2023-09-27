package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoSubcategory.SubCategoryRequest;
import peaksoft.dto.dtoSubcategory.SubCategoryResponse;
import peaksoft.service.SubCategoryService;

import java.util.List;

@RestController
@RequestMapping("/subCategories")
@RequiredArgsConstructor
public class SubCategoryAPI {
    private final SubCategoryService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAll")
    public List<SubCategoryResponse> getAllSubCategories(){
        return service.getAllSubCategory();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @PostMapping("/saveSubCategory")
    public SimpleResponse saveSubCategory(@RequestParam Long categoryId, @RequestBody SubCategoryRequest subCategoryRequest){
        return service.saveSubCategory(categoryId, subCategoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @PutMapping("/update/{id}")
    public SimpleResponse updateSubCategory(@PathVariable Long id, @RequestBody SubCategoryRequest subCategoryRequest){
        return service.updateSubCategory(id, subCategoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getById/{id}")
    public SubCategoryResponse getSubCategoryById(@PathVariable Long id){
        return service.getSubCategoryById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @DeleteMapping("{id}")
    public SimpleResponse deleteById(@PathVariable Long id){
        return service.deleteSubCategory(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/filterSubCategoryByCategoryName")
    public List<SubCategoryResponse> filterSubCategoryByCategoryName(@RequestParam String name){
        return service.filterSubCategoryByCategory(name);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAllSubCategoryByGroup")
    public List<SubCategoryResponse>getAllSubCategoryByGroup(){
        return service.getAllSubCategoryByGroup();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/search")
    public List<SubCategoryResponse> search(@RequestParam String word){
        return service.searchByName(word);
    }
}
