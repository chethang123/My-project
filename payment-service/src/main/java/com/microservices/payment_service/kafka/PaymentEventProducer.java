package com.microservices.payment_service.kafka;

import com.microservices.payment_service.Payment;
import com.microservices.payment_service.kafka.PaymentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventProducer {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentEventProducer(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentEvent(Payment payment) {
        PaymentEvent event = new PaymentEvent();

        event.setId(payment.getId());
        event.setUserId(payment.getUserId());
        event.setProductId(payment.getProductId());
        event.setQuantity(payment.getQuantity());
        event.setTotalPrice(payment.getTotalPrice());
        event.setOrderId(payment.getOrderId()); // NOW works: both are String
        event.setStatus(payment.getStatus());

        kafkaTemplate.send("payment-events", payment.getOrderId(), event);

        System.out.println("📤 PaymentEvent published for Order ID: " + payment.getOrderId());
    }
}
