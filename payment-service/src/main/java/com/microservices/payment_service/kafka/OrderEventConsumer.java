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
            groupId = "payment-service-group-v6",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderEvent event) {

        System.out.println("✅ Order received from Kafka");
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("Amount: " + event.getAmount());

        Payment payment = new Payment();
        payment.setOrderId(event.getOrderId().toString());
        payment.setAmount(event.getAmount());
        payment.setStatus("PAYMENT_PENDING");

        paymentRepository.save(payment);

        System.out.println("💾 Payment saved in DB");
    }

}
