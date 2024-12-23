package com.foodorder.controllers;

import com.foodorder.models.Restaurants;
import com.foodorder.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Create a new restaurant
    @PostMapping(path = "/add")
    public @ResponseBody Restaurants addNewRestaurant(@RequestBody Restaurants restaurant) {
        return restaurantRepository.save(restaurant);
    }

    // Read all restaurants
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Restaurants> getAllRestaurants() {
        return restaurantRepository.findAll();
    }



    // Read a specific restaurant by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<Restaurants> getRestaurantById(@PathVariable Long id) {
        Optional<Restaurants> restaurant = restaurantRepository.findById(id);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a restaurant by ID
    @PutMapping(path = "/update/{id}")
    public @ResponseBody ResponseEntity<Restaurants> updateRestaurant(@PathVariable Long id, @RequestBody Restaurants newRestaurant) {
        return restaurantRepository.findById(id).map(restaurant -> {
            restaurant.setName(newRestaurant.getName());
            restaurant.setLocation(newRestaurant.getLocation());
            return ResponseEntity.ok(restaurantRepository.save(restaurant));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a restaurant by ID
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
