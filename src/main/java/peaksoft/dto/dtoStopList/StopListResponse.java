package peaksoft.dto.dtoStopList;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StopListResponse(Long id,
                               String reason,
                               LocalDate date) {
    public StopListResponse {
    }
}
