package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoMenuItem.MenuItemRequest;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;

import java.util.List;

public interface MenuItemService {
    List<MenuItemResponse> getAllMenuItems();
    SimpleResponse saveMenuItem(Long restaurantId,Long suvCategoryId, MenuItemRequest menuItemRequest);
    SimpleResponse updateMenuItem(Long id,MenuItemRequest menuItemRequest);
    MenuItemResponse getMenuItemById(Long id);
    SimpleResponse deleteMenuItem(Long id);
  List<MenuItemResponse> searchByName(String word);
    List<MenuItemResponse> filterByIsVegetarian(boolean isVegetarian);
}
