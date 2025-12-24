package com.microservices.restaurant_service.repository;

import com.microservices.restaurant_service.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Restaurant findByName(String name);
}
