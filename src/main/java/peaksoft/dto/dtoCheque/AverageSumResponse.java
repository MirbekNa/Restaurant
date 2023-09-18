package peaksoft.dto.dtoCheque;

import lombok.Builder;

@Builder
public record AverageSumResponse(int sum) {
    public AverageSumResponse {
    }
}
