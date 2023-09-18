package peaksoft.dto.dtoUser;

import lombok.Builder;

import java.util.List;
@Builder
public record PaginationResponse(List<UserResponse> userResponseList,
                                 int size,
                                 int page) {
    public PaginationResponse {
    }
}
