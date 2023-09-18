package peaksoft.dto.dtoCategory;

import lombok.Builder;

@Builder
public record CategoryResponse(Long id,
                               String name) {
    public CategoryResponse {
    }
}
