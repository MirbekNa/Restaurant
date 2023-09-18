package peaksoft.dto.dtoAuth;

import lombok.Builder;

@Builder
public record AdminTokenRequest(String email) {
    public AdminTokenRequest {
    }
}
