package com.microservices.restaurant_service.services;

import com.microservices.restaurant_service.entity.Restaurant;
import com.microservices.restaurant_service.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant saveRestaurant(Restaurant r) {


        if (r.getFoodItems() != null) {
            r.getFoodItems().forEach(item -> item.setRestaurant(r));
        }

        return repository.save(r);
    }

    public List<Restaurant> getAllRestaurants() {
        return repository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }
    @Transactional
    public void deleteRestaurant(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Restaurant not found with id: " + id);
        }
        repository.deleteById(id);
    }

}
