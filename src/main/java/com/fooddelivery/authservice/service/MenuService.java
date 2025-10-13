package com.fooddelivery.authservice.service;

import com.fooddelivery.authservice.dto.MenuItemDTO;
import java.util.List;

public interface MenuService {
    List<MenuItemDTO> getMenuItemsByRestaurantId(Long restaurantId);
    MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO);
    MenuItemDTO updateMenuItem(Long id, MenuItemDTO menuItemDTO);
    void deleteMenuItem(Long id);
    List<MenuItemDTO> getMenuItemsByCategory(String category);
    List<MenuItemDTO> getMenuItemsByRestaurantAndCategory(Long restaurantId, String category);
}
