package peaksoft.dto.dtoSubcategory;

import lombok.Builder;

import java.util.List;
@Builder
public record PaginationSubCategoryResponse(List<SubCategoryResponse> subCategoryResponses,
                                            int currentPage,
                                            int pageSize) {
    public PaginationSubCategoryResponse {
    }
}
