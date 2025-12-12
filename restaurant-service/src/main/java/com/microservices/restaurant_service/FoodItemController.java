//package com.microservices.restaurant_service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/food")
//@CrossOrigin("*")
//public class FoodItemController {
//
//    @Autowired
//    private FoodItemRepository foodRepo;
//
//    @Autowired
//    private RestaurantRepository restaurantRepo;
//
//    @PostMapping("/add/{restaurantId}")
//    public FoodItem addFoodItem(
//            @PathVariable Long restaurantId,
//            @RequestBody FoodItem item
//    ) {
//        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow();
//        item.setRestaurant(restaurant);
//        return foodRepo.save(item);
//    }
//
//    @GetMapping("/restaurant/{restaurantId}")
//    public List<FoodItem> getFoodItems(@PathVariable Long restaurantId) {
//        return foodRepo.findByRestaurantId(restaurantId);
//    }
//}
