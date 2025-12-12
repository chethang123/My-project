package com.microservices.order_service;

import com.microservices.order_service.RestaurantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public Order create(@RequestBody Order order) {
        return orderService.createOrder(order);
    }


    @GetMapping
    public List<Order> getAll() {
        return orderService.getAllOrders();
    }


    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }


    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }


    @GetMapping("/restaurant/{id}")
    public RestaurantResponse getRestaurant(@PathVariable Long id) {
        return orderService.fetchRestaurant(id);
    }
}
