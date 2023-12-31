package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoRestaurant.RestaurantRequest;
import peaksoft.dto.dtoRestaurant.RestaurantResponse;
import peaksoft.exceptions.BadRequestException;
import peaksoft.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantAPI {
    private final RestaurantService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        return service.saveRestaurant(restaurantRequest);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<RestaurantRequest> getAllRestaurants() {
        return service.getAllRestaurant();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public SimpleResponse updateRestaurant(@PathVariable Long id,@RequestBody RestaurantRequest restaurantRequest){
        return service.updateRestaurant(id, restaurantRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getById/{id}")
    public RestaurantResponse getById(@PathVariable Long id){
        return service.getRestaurantById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteRestaurant(@PathVariable Long id) throws BadRequestException {
        return service.deleteRestaurant(id);
    }
}
