package com.foodorder.repositories;

import com.foodorder.models.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders, Long> {

}
