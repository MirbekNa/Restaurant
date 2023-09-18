package peaksoft.dto.dtoCategory;

import lombok.Builder;

import java.util.List;
@Builder
public record PaginationResponse(List<CategoryResponse> categories,
                                 int currentPage,
                                 int pageSize) {
    public PaginationResponse {
    }
}
