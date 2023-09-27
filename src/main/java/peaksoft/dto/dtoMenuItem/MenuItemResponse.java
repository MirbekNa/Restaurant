package peaksoft.dto.dtoMenuItem;

import lombok.Builder;
import lombok.Getter;


@Builder
public record MenuItemResponse(Long id,
                               String name,
                               String image,
                               int price,
                               String description,
                               boolean isVegetarian) {
    public MenuItemResponse {
    }
}
