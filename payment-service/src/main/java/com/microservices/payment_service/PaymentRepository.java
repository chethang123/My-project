package com.microservices.payment_service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // use String since orderId is now String
    Payment findByOrderId(String orderId);
}
