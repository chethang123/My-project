package com.microservices.restaurant_service.services;

import com.microservices.restaurant_service.entity.*;
import com.microservices.restaurant_service.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodItemService {

    private final FoodItemRepository foodItemRepo;
    private final RestaurantRepository restaurantRepo;

    public FoodItemService(FoodItemRepository foodItemRepo,RestaurantRepository restaurantRepo) {
        this.foodItemRepo = foodItemRepo;
        this.restaurantRepo = restaurantRepo;
    }

    public FoodItem addFoodItem(Long restaurantId, FoodItem item) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        item.setRestaurant(restaurant);
        return foodItemRepo.save(item);
    }

    public List<FoodItem> getFoodItemsByRestaurantId(Long restaurantId) {
        return foodItemRepo.findByRestaurantId(restaurantId);
    }
}
