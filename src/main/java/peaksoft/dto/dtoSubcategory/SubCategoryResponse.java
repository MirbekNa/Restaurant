package peaksoft.dto.dtoSubcategory;

import lombok.Builder;

@Builder
public record SubCategoryResponse(Long id, String name,String categoryName) {
    public SubCategoryResponse {
    }

    public SubCategoryResponse(Long id, String name) {
        this(id, name, null);
    }

}
