package peaksoft.dto.dtoStopList;

import lombok.Builder;

import java.util.List;
@Builder
public record PaginationStopListResponse(List<StopListResponse> stopListResponses,
                                         int currentPage,
                                         int pageSize) {
    public PaginationStopListResponse {
    }
}
