package com.microservices.restaurant_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    @PostMapping("/create")
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return service.saveRestaurant(restaurant);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable Long id) {
        return service.getRestaurantById(id);
    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        return service.updateRestaurant(id, restaurant);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteRestaurant(id);
    }
}
