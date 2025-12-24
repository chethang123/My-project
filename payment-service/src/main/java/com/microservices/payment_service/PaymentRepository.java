package com.microservices.payment_service;

import com.microservices.payment_service.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByOrderId(String orderId);
}
