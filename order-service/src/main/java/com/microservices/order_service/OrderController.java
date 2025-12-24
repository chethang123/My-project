package com.microservices.order_service;

import com.microservices.order_service.kafka.OrderEvent;
import com.microservices.order_service.kafka.OrderEventProducer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
//@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    private final OrderService orderService;
    private final OrderEventProducer orderEventProducer; // Kafka producer

    public OrderController(OrderService orderService, OrderEventProducer orderEventProducer) {
        this.orderService = orderService;
        this.orderEventProducer = orderEventProducer;
    }

    // ✅ CREATE ORDER AND SEND KAFKA EVENT
    @PostMapping("/create")
    public Order create(@RequestBody Order order) {
        order.setStatus("PAID");
        Order savedOrder = orderService.createOrder(order);

        // Build Kafka event
        OrderEvent event = new OrderEvent();
        event.setOrderId(savedOrder.getId());
        event.setRestaurantName(savedOrder.getRestaurantName());
        event.setAmount(savedOrder.getTotalPrice());
        event.setStatus(savedOrder.getStatus());

        // Publish event to Kafka
        orderEventProducer.sendOrderEvent(event);

        return savedOrder;
    }

    // ✅ GET ALL ORDERS
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // ✅ DELETE ORDER
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
