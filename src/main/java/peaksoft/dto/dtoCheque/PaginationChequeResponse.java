package peaksoft.dto.dtoCheque;

import lombok.Builder;

import java.util.List;
@Builder
public record PaginationChequeResponse(List<ChequeResponse> chequeResponses,
                                       int currentPage,
                                       int pageSize) {
    public PaginationChequeResponse {
    }
}
