package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoRestaurant.RestaurantRequest;
import peaksoft.dto.dtoRestaurant.RestaurantResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.*;
import peaksoft.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final StopListRepository stopListRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<RestaurantRequest> getAllRestaurant() {
        return null;
    }

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        User admin = userRepository.findById(1L).orElseThrow(() -> new NotFoundException("Admin is not found"));
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setService(restaurantRequest.service());
        restaurant.setUsers(new ArrayList<>(List.of(admin)));
        restaurant.setNumberOfEmployees(restaurantRequest.numberOfEmployees());
        repository.save(restaurant);
        admin.setRestaurant(restaurant);
        userRepository.save(admin);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully created")
                .build();
    }

    @Override
    public SimpleResponse updateRestaurant(Long id, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = repository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant is not found"));
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setService(restaurantRequest.service());
        restaurant.setNumberOfEmployees(restaurantRequest.numberOfEmployees());
        repository.save(restaurant);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated...")
                .build();
    }

    @Override
    public RestaurantResponse getRestaurantById(Long id) {
        Restaurant restaurant = repository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant is not found"));
        List<User> allUsers = repository.getAllUsers();
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .numberOfEmployees(allUsers.size())
                .service(restaurant.getService())
                .restType(restaurant.getRestType())
                .build();
    }

    @Override
    public SimpleResponse deleteRestaurant(Long id) throws BadRequestException {
        if (repository.existsById(id)) {
            Restaurant restaurant = repository.findById(id).orElseThrow(() -> new NotFoundException("restaurant with id is not found"));
            categoryRepository.deleteAll();
            repository.deleteById(id);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Successfully deleted")
                    .build();
        } else throw new BadRequestException("Restaurant with id is not found");
    }
}
