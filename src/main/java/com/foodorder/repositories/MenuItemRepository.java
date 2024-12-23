package com.foodorder.repositories;

import com.foodorder.models.Menu_Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<Menu_Items, Long> {
    List<Menu_Items> findByCategory(String category);
}
