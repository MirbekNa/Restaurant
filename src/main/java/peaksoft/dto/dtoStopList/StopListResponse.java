package peaksoft.dto.dtoStopList;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Builder
@Setter
@Getter
public class StopListResponse {
    private Long id;
    private String reason;
    private LocalDate date;
    private String productName;

    public StopListResponse(Long id, String reason, LocalDate date, String productName) {
        this.id = id;
        this.reason = reason;
        this.date = date;
        this.productName = productName;
    }

    // Геттеры для всех полей, включая productName
}
