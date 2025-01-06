package com.foodorder.controllers;

import com.foodorder.models.Users;
import com.foodorder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;



    // Create a new user
    @PostMapping(path = "/add")
    public @ResponseBody Users addNewUser(@RequestBody Users user) {
        return userRepository.save(user);
    }

    // Read all users
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        return userRepository.findAll();
    }



    // Read a specific user by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Optional<Users> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a user by ID
    @PutMapping(path = "/update/{id}")
    public @ResponseBody ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            return ResponseEntity.ok(userRepository.save(user));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a user by ID
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
