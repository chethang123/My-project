package com.microservices.payment_service;

import java.util.Map;

public interface PaymentService {

    Map<String, Object> createRazorpayOrder(int amount) throws Exception;

    boolean verifyPayment(Map<String, String> data);
}