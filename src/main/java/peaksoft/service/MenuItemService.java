package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoMenuItem.MenuItemRequest;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;
import peaksoft.dto.dtoMenuItem.PaginationMenuItemResponse;

public interface MenuItemService {
    PaginationMenuItemResponse getAllMenuItems(String ascDesc,int currentPage, int pageSize);
    SimpleResponse saveMenuItem(Long restaurantId,Long suvCategoryId, MenuItemRequest menuItemRequest);
    SimpleResponse updateMenuItem(Long id,MenuItemRequest menuItemRequest);
    MenuItemResponse getMenuItemById(Long id);
    SimpleResponse deleteMenuItem(Long id);
    PaginationMenuItemResponse searchByName(String word,int currentPage, int pageSize);
    PaginationMenuItemResponse filterByIsVegetarian(boolean isVegetarian,int currentPage, int pageSize);
}
