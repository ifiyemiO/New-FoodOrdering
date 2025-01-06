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
    public @ResponseBody Orders addNewOrder(@RequestBody Orders order) {
        if (order.getTotalAmount() == null) {
            order.setTotalAmount(0.0); // Set default value if not provided
        }
        return orderRepository.save(order);
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
