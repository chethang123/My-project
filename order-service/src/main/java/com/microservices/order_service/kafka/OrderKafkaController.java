//package com.microservices.order_service.kafka;
//
//import com.microservices.order_service.Order;
//import com.microservices.order_service.kafka.OrderEvent;
//import com.microservices.order_service.kafka.OrderProducer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/orders/kafka")
//public class OrderKafkaController {
//
//    @Autowired
//    private OrderProducer orderProducer;
//
//    @PostMapping("/publish")
//    public String publishOrder(@RequestBody Order order) {
//
//        OrderEvent event = new OrderEvent();
//        event.setOrderId(order.getId());
//        event.setRestaurantName(order.getRestaurantName());
//        event.setFoodItem(order.getFoodItem());
//        event.setQuantity(order.getQuantity());
//        event.setTotalPrice(order.getTotalPrice());
//        event.setCustomerName(order.getCustomerName());
//        event.setCustomerPhone(order.getCustomerPhone());
//        event.setDeliveryAddress(order.getDeliveryAddress());
//        event.setSource("food-app");
//        event.setStatus("PENDING");
//
//        orderProducer.sendOrder(event);
//
//        return "Order sent to payment-service via Kafka!";
//    }
//}
