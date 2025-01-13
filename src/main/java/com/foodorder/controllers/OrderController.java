package com.foodorder.controllers;

import com.foodorder.models.Orders;
import com.foodorder.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/orders")

public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // Create a new order
    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<?> addNewOrder(@RequestBody Orders order) {
        // Validate required fields
        if (order.getCustomerName() == null || order.getCustomerName().isEmpty()) {
            return ResponseEntity.badRequest().body("Customer name is required.");
        }
        if (order.getRestaurantName() == null || order.getRestaurantName().isEmpty()) {
            return ResponseEntity.badRequest().body("Restaurant name is required.");
        }
        if (order.getRestaurantLocation() == null || order.getRestaurantLocation().isEmpty()) {
            return ResponseEntity.badRequest().body("Restaurant location is required.");
        }

        // Set default value for totalAmount if not provided
        if (order.getTotalAmount() == null) {
            order.setTotalAmount(0.0);
        }

        try {
            Orders savedOrder = orderRepository.save(order);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            // Log the error and return an appropriate response
            return ResponseEntity.status(500).body("An error occurred while saving the order: " + e.getMessage());
        }
    }

    // Read all orders
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    // Read a specific order by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Optional<Orders> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an order by ID
    @PutMapping(path = "/update/{id}")
    public @ResponseBody ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders newOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setCustomerName(newOrder.getCustomerName());
            order.setOrderDate(newOrder.getOrderDate());
            order.setTotalAmount(newOrder.getTotalAmount() != null ? newOrder.getTotalAmount() : 0.0);
            return ResponseEntity.ok(orderRepository.save(order));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete an order by ID
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}