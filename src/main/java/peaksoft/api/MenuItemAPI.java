package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoMenuItem.MenuItemRequest;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;
import peaksoft.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/menuItems")
@RequiredArgsConstructor
public class MenuItemAPI {
    private final MenuItemService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping("/save")
    public SimpleResponse saveMenuItem(@RequestParam Long restaurantId,
                                       @RequestParam Long subCategoryId,
                                       @RequestBody @Valid MenuItemRequest menuItemRequest) {
        return service.saveMenuItem(restaurantId, subCategoryId, menuItemRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/update/{id}")
    public SimpleResponse updateMenuItem(@PathVariable Long id, @RequestBody MenuItemRequest menuItemRequest) {
        return service.updateMenuItem(id, menuItemRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getById/{id}")
    public MenuItemResponse getMenuItemById(@PathVariable Long id) {
        return service.getMenuItemById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/delete/{id}")
    public SimpleResponse deleteMenuItem(@PathVariable Long id) {
        return service.deleteMenuItem(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAll")
    public List<MenuItemResponse> getAllMenuItems() {
        return service.getAllMenuItems();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/searchByName")
    public List<MenuItemResponse> searchByName(@RequestParam String word) {
        return service.searchByName(word);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/filterByIsVegetarian")
    public List<MenuItemResponse> filterByIsVegetarian(@RequestParam boolean isVegetarian) {
        return service.filterByIsVegetarian(isVegetarian);
    }
}
