package peaksoft.dto.dtoRestaurant;

import lombok.Builder;

@Builder
public record RestaurantRequest(String name,
                                String location,
                                String restType,
                                int numberOfEmployees,
                                int service) {
    public RestaurantRequest {
    }
}
