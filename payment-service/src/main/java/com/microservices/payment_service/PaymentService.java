package com.microservices.payment_service;

import java.util.Map;

public interface PaymentService {

    Map<String, Object> createRazorpayOrder(int amount) throws Exception;

    Map<String, Object> createOrder(int amount, Long userId, Long productId, int quantity) throws Exception;

    boolean verifyPayment(Map<String, String> data);


}