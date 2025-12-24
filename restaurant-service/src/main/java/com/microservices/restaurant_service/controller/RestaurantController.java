package com.microservices.restaurant_service.controller;

import com.microservices.restaurant_service.entity.Restaurant;
import com.microservices.restaurant_service.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
//@CrossOrigin(origins = "http://localhost:4200")
public class RestaurantController {

    @Autowired
     RestaurantService service;



    @PostMapping("/create")
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return service.saveRestaurant(restaurant);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAllRestaurants();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRestaurant(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
