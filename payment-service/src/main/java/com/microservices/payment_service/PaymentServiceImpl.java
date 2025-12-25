package com.microservices.payment_service;

import com.microservices.payment_service.kafka.PaymentEventProducer;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer paymentEventProducer;

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.secret}")
    private String secret;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              PaymentEventProducer paymentEventProducer) {
        this.paymentRepository = paymentRepository;
        this.paymentEventProducer = paymentEventProducer;
    }

    @Override
    public Map<String, Object> createOrder(int amount, Long userId, Long productId, int quantity) throws Exception {
        RazorpayClient client = new RazorpayClient(key, secret);

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // paise
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = client.orders.create(options);

        Payment payment = new Payment();
        payment.setOrderId(order.get("id"));
        payment.setUserId(userId);
        payment.setProductId(productId);
        payment.setQuantity(quantity);
        payment.setTotalPrice(amount);
        payment.setStatus("CREATED");
        payment.setAmount(amount);
        payment.setTotalPrice(amount);

        paymentRepository.save(payment);

        // Publish Kafka event
        paymentEventProducer.publishPaymentEvent(payment);

        Map<String, Object> response = new HashMap<>();
        response.put("id", order.get("id"));
        response.put("amount", order.get("amount"));
        response.put("currency", order.get("currency"));
        return response;
    }

    @Override
    public Map<String, Object> createRazorpayOrder(int amount) throws Exception {
        // Optional: you can implement a simpler method if you just need amount
        return createOrder(amount, 1L, 1L, 1); // example default values
    }

    @Override
    public boolean verifyPayment(Map<String, String> data) {
        // Implement Razorpay signature verification here
        return false;
    }
}
