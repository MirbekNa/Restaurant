package peaksoft.dto.dtoCheque;

import lombok.Builder;

import java.util.List;

@Builder
public record ChequeRequest(List<Long> menuItemId) {
    public ChequeRequest {
    }
}
