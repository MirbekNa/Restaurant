package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoMenuItem.MenuItemRequest;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;
import peaksoft.dto.dtoMenuItem.PaginationMenuItemResponse;
import peaksoft.service.MenuItemService;

@RestController
@RequestMapping("/menuItems")
@RequiredArgsConstructor
public class MenuItemAPI {
    private final MenuItemService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAll")
    public PaginationMenuItemResponse getAllMenuItems(@RequestParam String ascDesc,
                                                       @RequestParam int currentPage,
                                                      @RequestParam  int pageSize){
        return service.getAllMenuItems(ascDesc, currentPage, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping("/save")
    public SimpleResponse saveMenuItem(@RequestParam Long restaurantId,
                                       @RequestParam Long SubCategoryId,
                                       @RequestBody @Valid MenuItemRequest menuItemRequest){
        return service.saveMenuItem(restaurantId, SubCategoryId, menuItemRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/update")
    public  SimpleResponse updateMenuItem(@RequestParam long id,@RequestBody MenuItemRequest menuItemRequest){
        return service.updateMenuItem(id,menuItemRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("{getById}")
    public MenuItemResponse getMenuItemById(@PathVariable Long getById){
        return service.getMenuItemById(getById);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("{id}")
    public SimpleResponse deleteMenuItem(@PathVariable Long id){
        return service.deleteMenuItem(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/search")
    public  PaginationMenuItemResponse search(@RequestParam String word,
                                              @RequestParam int currentPage,
                                              @RequestParam  int pageSize){
        return service.searchByName(word, currentPage, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/filter")
    public  PaginationMenuItemResponse filterIsVegetarian(@RequestParam boolean isVegetarian,
                                              @RequestParam int currentPage,
                                              @RequestParam  int pageSize){
        return service.filterByIsVegetarian(isVegetarian, currentPage, pageSize);
    }
}
