package peaksoft.dto.dtoMenuItem;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record MenuItemRequest(String name,
                              String image,
                              @Min(value = 1,message = "price must not be below 1")
                              int price,
                              String description,
                              boolean isVegetarian) {
    public MenuItemRequest {
    }
}
