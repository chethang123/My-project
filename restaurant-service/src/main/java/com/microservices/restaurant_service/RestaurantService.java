package com.microservices.restaurant_service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant saveRestaurant(Restaurant r) {
        return repository.save(r);
    }

    public List<Restaurant> getAllRestaurants() {
        return repository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }

    @Transactional
    public Restaurant updateRestaurant(Long id, Restaurant r) {
        Restaurant existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));

        existing.setName(r.getName());
        existing.setLocation(r.getLocation());
        existing.setCuisineType(r.getCuisineType());
        existing.setPhoneNumber(r.getPhoneNumber());
        existing.setRating(r.getRating());

        return repository.save(existing);
    }

    @Transactional
    public void deleteRestaurant(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Restaurant not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
