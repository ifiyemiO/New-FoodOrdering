package com.foodorder.repositories;

import com.foodorder.models.Restaurants;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurants, Long> {
}


