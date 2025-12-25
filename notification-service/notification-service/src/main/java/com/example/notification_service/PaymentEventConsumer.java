package com.example.notification_service;

import com.example.notification_service.Notification;
import com.example.notification_service.event.PaymentEvent;
import com.example.notification_service.NotificationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventConsumer {

    private final NotificationRepository repository;

    public PaymentEventConsumer(NotificationRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
            topics = "payment-events",
            groupId = "notification-group"
    )
    public void consumePaymentEvent(PaymentEvent event) {

        System.out.println("📥 Payment Event Received");
        System.out.println("Order ID   : " + event.getOrderId());
        System.out.println("Payment ID : " + event.getPaymentId());
        System.out.println("Amount     : ₹" + event.getAmount());
        System.out.println("Status     : " + event.getStatus());

        if ("SUCCESS".equalsIgnoreCase(event.getStatus())) {

            Notification notification = new Notification();
            notification.setOrderId(Long.valueOf(event.getOrderId()));
            notification.setAmount(event.getAmount());
            notification.setStatus(event.getStatus());
            notification.setMessage(
                    "Payment SUCCESS for Order ID " + event.getOrderId()
            );

            repository.save(notification);

            System.out.println("📦 Notification saved to DB");
        }
    }
}
