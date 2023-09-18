package peaksoft.dto.dtoCategory;

import lombok.Builder;

@Builder
public record CategoryRequest(String name) {
    public CategoryRequest {
    }
}
