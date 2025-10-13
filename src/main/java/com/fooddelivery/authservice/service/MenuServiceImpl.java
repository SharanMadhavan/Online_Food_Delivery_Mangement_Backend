package com.fooddelivery.authservice.service;

import com.fooddelivery.authservice.dto.MenuItemDTO;
import com.fooddelivery.authservice.entity.MenuItem;
import com.fooddelivery.authservice.entity.Restaurant;
import com.fooddelivery.authservice.repository.MenuItemRepository;
import com.fooddelivery.authservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<MenuItemDTO> getMenuItemsByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantIdAndAvailableTrue(restaurantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO) {
        Restaurant restaurant = restaurantRepository.findById(menuItemDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + menuItemDTO.getRestaurantId()));
        
        MenuItem menuItem = convertToEntity(menuItemDTO, restaurant);
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return convertToDTO(savedMenuItem);
    }

    @Override
    public MenuItemDTO updateMenuItem(Long id, MenuItemDTO menuItemDTO) {
        MenuItem existingMenuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));
        
        existingMenuItem.setName(menuItemDTO.getName());
        existingMenuItem.setDescription(menuItemDTO.getDescription());
        existingMenuItem.setPrice(menuItemDTO.getPrice());
        existingMenuItem.setCategory(menuItemDTO.getCategory());
        existingMenuItem.setImageUrl(menuItemDTO.getImageUrl());
        existingMenuItem.setAvailable(menuItemDTO.getAvailable());
        
        MenuItem updatedMenuItem = menuItemRepository.save(existingMenuItem);
        return convertToDTO(updatedMenuItem);
    }

    @Override
    public void deleteMenuItem(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("Menu item not found with id: " + id);
        }
        menuItemRepository.deleteById(id);
    }

    @Override
    public List<MenuItemDTO> getMenuItemsByCategory(String category) {
        return menuItemRepository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> getMenuItemsByRestaurantAndCategory(Long restaurantId, String category) {
        return menuItemRepository.findByRestaurantIdAndCategory(restaurantId, category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MenuItemDTO convertToDTO(MenuItem menuItem) {
        return new MenuItemDTO(
                menuItem.getId(),
                menuItem.getRestaurant().getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getCategory(),
                menuItem.getImageUrl(),
                menuItem.getAvailable()
        );
    }

    private MenuItem convertToEntity(MenuItemDTO menuItemDTO, Restaurant restaurant) {
        return new MenuItem(
                restaurant,
                menuItemDTO.getName(),
                menuItemDTO.getDescription(),
                menuItemDTO.getPrice(),
                menuItemDTO.getCategory(),
                menuItemDTO.getImageUrl()
        );
    }
}
