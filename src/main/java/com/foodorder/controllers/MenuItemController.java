package com.foodorder.controllers;

import com.foodorder.models.Menu_Items;
import com.foodorder.repositories.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/menu_items")
public class MenuItemController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    // Create a new menu item
    @PostMapping(path = "/add")
    public @ResponseBody Menu_Items addNewMenuItem(@RequestBody Menu_Items menuItem) {
        return menuItemRepository.save(menuItem);
    }

    // Read all menu items
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Menu_Items> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    // Fetch menu items by category
    @GetMapping(path = "/category/{category}")
    public List<Menu_Items> getMenuItemsByCategory(@PathVariable String category) {
        return menuItemRepository.findByCategory(category);
    }

    // Read a specific menu item by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<Menu_Items> getMenuItemById(@PathVariable Long id) {
        Optional<Menu_Items> menuItem = menuItemRepository.findById(id);
        return menuItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a menu item by ID
    @PutMapping(path = "/update/{id}")
    public @ResponseBody ResponseEntity<Menu_Items> updateMenuItem(@PathVariable Long id, @RequestBody Menu_Items newMenuItem) {
        return menuItemRepository.findById(id).map(menuItem -> {
            menuItem.setName(newMenuItem.getName());
            menuItem.setDescription(newMenuItem.getDescription());
            menuItem.setPrice(newMenuItem.getPrice());
            menuItem.setCategory(newMenuItem.getCategory());
            return ResponseEntity.ok(menuItemRepository.save(menuItem));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a menu item by ID
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        if (menuItemRepository.existsById(id)) {
            menuItemRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
