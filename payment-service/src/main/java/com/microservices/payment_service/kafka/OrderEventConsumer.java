package com.microservices.payment_service.kafka;

import com.microservices.payment_service.Payment;
import com.microservices.payment_service.PaymentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    private final PaymentRepository paymentRepository;

    public OrderEventConsumer(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @KafkaListener(
            topics = "ordertopic1",
            groupId = "payment-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderEvent event) {
        System.out.println("✅ Order received from Kafka: " + event.getOrderId());

        Payment payment = new Payment();
        payment.setOrderId(event.getOrderId());
        payment.setUserId(event.getUserId());
        payment.setProductId(event.getProductId());
        payment.setQuantity(event.getQuantity());
        payment.setTotalPrice(event.getTotalPrice());
        payment.setStatus("PAYMENT_PENDING");

        paymentRepository.save(payment);

        System.out.println("💾 Payment saved for Order ID: " + event.getOrderId());
    }
}
