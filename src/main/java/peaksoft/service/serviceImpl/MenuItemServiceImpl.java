package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoMenuItem.MenuItemRequest;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;
import peaksoft.dto.dtoMenuItem.PaginationMenuItemResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.StopList;
import peaksoft.entity.SubCategory;
import peaksoft.exceptions.NoSuchElementException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.MenuItemService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final StopListRepository stopListRepository;

    @Override
    public PaginationMenuItemResponse getAllMenuItems(String ascDesc, int currentPage, int pageSize) {
        if (ascDesc.equals("asc")){
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<MenuItemResponse> allMenuItems = repository.getAllMenuItems(ascDesc, pageable);
        return PaginationMenuItemResponse.builder()
                .menuItemResponses(allMenuItems.getContent())
                .currentPage(allMenuItems.getNumber()+1)
                .pageSize(allMenuItems.getTotalPages())
                .build();
        }else if (ascDesc.equals("desc")){
            Pageable pageable = PageRequest.of(currentPage-1,pageSize);
            Page<MenuItemResponse> allMenuItems = repository.getAllMenuItemsDesc(ascDesc, pageable);
            return PaginationMenuItemResponse.builder()
                    .menuItemResponses(allMenuItems.getContent())
                    .currentPage(allMenuItems.getNumber()+1)
                    .pageSize(allMenuItems.getTotalPages())
                    .build();
        }
        return null;
    }

    @Override
    public SimpleResponse saveMenuItem(Long restaurantId, Long suvCategoryId, MenuItemRequest menuItemRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NoSuchElementException(String.format("Restaurant with id:%s does not exist", restaurantId)));
        SubCategory subCategory = subCategoryRepository.findById(suvCategoryId).orElseThrow(() -> new NoSuchElementException(String.format("SubCategory with id:%s does not exist", suvCategoryId)));
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.name());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setRestaurant(restaurant);
        menuItem.setSubCategory(subCategory);
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        repository.save(menuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved...")
                .build();
    }

    @Override
    public SimpleResponse updateMenuItem(Long id, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("MenuItem with id:%s does not exist", id)));
        menuItem.setName(menuItemRequest.name());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        repository.save(menuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved...")
                .build();
    }

    @Override
    public MenuItemResponse getMenuItemById(Long id) {
        MenuItem menuItem = repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("MenuItem with id:%s does not exist", id)));
        List<StopList> all = stopListRepository.findAll();
        for (StopList s: all) {
            if (s.getMenuItem().equals(menuItem) && s.getDate().equals(LocalDate.now())){
                return MenuItemResponse.builder()
                        .name("This menuItem temporarily no available")
                        .build();
            }
        }
        return MenuItemResponse.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .description(menuItem.getDescription())
                .image(menuItem.getImage())
                .isVegetarian(menuItem.isVegetarian())
                .build();
    }

    @Override
    public SimpleResponse deleteMenuItem(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
            return  SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Successfully deleted...")
                    .build();
        }else throw new  NoSuchElementException(String.format("MenuItem with id:%s does not exist", id));

    }

    @Override
    public PaginationMenuItemResponse searchByName(String word, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<MenuItemResponse> search = repository.search(word, pageable);
        return PaginationMenuItemResponse.builder()
                .menuItemResponses(search.getContent())
                .currentPage(search.getNumber()+1)
                .pageSize(search.getTotalPages())
                .build();
    }

    @Override
    public PaginationMenuItemResponse filterByIsVegetarian(boolean isVegetarian, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<MenuItemResponse> search = repository.filterByIsVegetarian(isVegetarian, pageable);
        return PaginationMenuItemResponse.builder()
                .menuItemResponses(search.getContent())
                .currentPage(search.getNumber()+1)
                .pageSize(search.getTotalPages())
                .build();
    }
}
