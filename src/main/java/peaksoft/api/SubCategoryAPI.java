package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoSubcategory.PaginationSubCategoryResponse;
import peaksoft.dto.dtoSubcategory.SubCategoryRequest;
import peaksoft.dto.dtoSubcategory.SubCategoryResponse;
import peaksoft.service.SubCategoryService;

@RestController
@RequestMapping("/subCategories")
@RequiredArgsConstructor
public class  SubCategoryAPI {
    private final SubCategoryService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAll")
    public PaginationSubCategoryResponse getAllSubCategories(@RequestParam int currentPage, @RequestParam int pageSize){
        return service.getAllSubCategory(currentPage, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @PostMapping("/saveSubCategory")
    public SimpleResponse saveSubCategory(@RequestParam Long categoryId,@RequestBody SubCategoryRequest subCategoryRequest){
        return service.saveSubCategory(categoryId, subCategoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @PutMapping("/update/{id}")
    public SimpleResponse updateSubCategory(@PathVariable Long id,@RequestBody SubCategoryRequest subCategoryRequest){
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
    public PaginationSubCategoryResponse filterSubCategoryByCategoryName(@RequestParam String name,
                                                                         @RequestParam int currentPage ,
                                                                         @RequestParam int pageSize){
        return service.filterSubCategoryByCategory(name, currentPage, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/byGroup")
    public PaginationSubCategoryResponse getSubCategoryByGroup(@RequestParam int currentPage ,
                                                               @RequestParam int pageSize){
        return service.getAllSubCategoryByGroup(currentPage, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/search")
    public PaginationSubCategoryResponse search(@RequestParam String word,
                                                @RequestParam int currentPage ,
                                                @RequestParam int pageSize){
        return service.searchByName(word, currentPage, pageSize);
    }
}
