package com.microservices.restaurant_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microservices.restaurant_service.entity.FoodItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findByRestaurantId(Long restaurantId);
}
