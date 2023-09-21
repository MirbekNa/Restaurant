package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoRestaurant.RestaurantRequest;
import peaksoft.dto.dtoRestaurant.RestaurantResponse;
import peaksoft.exceptions.BadRequestException;

import java.util.List;

public interface RestaurantService {
    List<RestaurantRequest> getAllRestaurant();
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);
    SimpleResponse updateRestaurant(Long id, RestaurantRequest restaurantRequest);
    RestaurantResponse getRestaurantById(Long id);
    SimpleResponse deleteRestaurant(Long id) throws BadRequestException;

}
