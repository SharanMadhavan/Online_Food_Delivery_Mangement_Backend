package com.fooddelivery.authservice.controller;

import com.fooddelivery.authservice.dto.MenuItemDTO;
import com.fooddelivery.authservice.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@Tag(name = "Menu Management", description = "APIs for managing menu items")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/restaurant/{restaurantId}")
    @Operation(summary = "Get menu items by restaurant", description = "Retrieve all menu items for a specific restaurant")
    public ResponseEntity<List<MenuItemDTO>> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        List<MenuItemDTO> menuItems = menuService.getMenuItemsByRestaurantId(restaurantId);
        return ResponseEntity.ok(menuItems);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create menu item", description = "Create a new menu item (Admin only)")
    public ResponseEntity<MenuItemDTO> createMenuItem(@Valid @RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO createdMenuItem = menuService.createMenuItem(menuItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenuItem);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update menu item", description = "Update an existing menu item (Admin only)")
    public ResponseEntity<MenuItemDTO> updateMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO updatedMenuItem = menuService.updateMenuItem(id, menuItemDTO);
        return ResponseEntity.ok(updatedMenuItem);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete menu item", description = "Delete a menu item (Admin only)")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get menu items by category", description = "Retrieve menu items filtered by category")
    public ResponseEntity<List<MenuItemDTO>> getMenuItemsByCategory(@PathVariable String category) {
        List<MenuItemDTO> menuItems = menuService.getMenuItemsByCategory(category);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/restaurant/{restaurantId}/category/{category}")
    @Operation(summary = "Get menu items by restaurant and category", description = "Retrieve menu items filtered by restaurant and category")
    public ResponseEntity<List<MenuItemDTO>> getMenuItemsByRestaurantAndCategory(
            @PathVariable Long restaurantId, @PathVariable String category) {
        List<MenuItemDTO> menuItems = menuService.getMenuItemsByRestaurantAndCategory(restaurantId, category);
        return ResponseEntity.ok(menuItems);
    }
}
