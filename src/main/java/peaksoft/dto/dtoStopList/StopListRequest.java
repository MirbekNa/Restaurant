package peaksoft.dto.dtoStopList;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StopListRequest(String reason,
                              LocalDate date) {
    public StopListRequest {
    }
}
