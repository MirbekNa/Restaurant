package peaksoft.dto.dtoSubcategory;

import lombok.Builder;

@Builder
public record SubCategoryRequest(String name) {
    public SubCategoryRequest {
    }
}
