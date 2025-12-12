package com.microservices.order_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    private final RestaurantClient restaurantClient;

    public OrderService(RestaurantClient restaurantClient) {
        this.restaurantClient = restaurantClient;
    }

    public RestaurantResponse fetchRestaurant(Long restaurantId) {
        return restaurantClient.getRestaurantById(restaurantId);
    }
    public Order createOrder(Order order) {
        order.setStatus("CREATED");
        return repository.save(order);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order getOrderById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Order updateOrder(Long id, Order newOrder) {
        Order existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setRestaurantId(newOrder.getRestaurantId());
            existing.setCustomerName(newOrder.getCustomerName());
            existing.setCustomerPhone(newOrder.getCustomerPhone());
            existing.setDeliveryAddress(newOrder.getDeliveryAddress());
            existing.setFoodItem(newOrder.getFoodItem());
            existing.setQuantity(newOrder.getQuantity());
            existing.setTotalPrice(newOrder.getTotalPrice());
            existing.setStatus(newOrder.getStatus());
            return repository.save(existing);
        }
        return null;
    }

    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}
