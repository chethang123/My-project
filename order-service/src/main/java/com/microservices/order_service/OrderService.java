package com.microservices.order_service;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public Order createOrder(Order order) {
        order.setStatus("PAID");
        return repository.save(order);
    }

    // GET ALL
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    // DELETE
    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}
