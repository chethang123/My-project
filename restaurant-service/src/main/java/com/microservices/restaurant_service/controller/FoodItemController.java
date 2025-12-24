package com.microservices.restaurant_service.controller;

import com.microservices.restaurant_service.entity.FoodItem;
import com.microservices.restaurant_service.services.FoodItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
//@CrossOrigin(origins = "http://localhost:4200")
public class FoodItemController {

    private final FoodItemService service;

    public FoodItemController(FoodItemService service) {
        this.service = service;
    }

    @PostMapping("/{restaurantId}/items")
    public FoodItem addFoodItem(
            @PathVariable Long restaurantId,
            @RequestBody FoodItem foodItem) {
        return service.addFoodItem(restaurantId, foodItem);
    }

    @GetMapping("/{restaurantId}/items")
    public List<FoodItem> getFoodItems(@PathVariable Long restaurantId) {
        return service.getFoodItemsByRestaurantId(restaurantId);
    }
}
