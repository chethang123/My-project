package com.microservices.payment_service;

import com.microservices.payment_service.PaymentService;
import com.microservices.payment_service.Payment;
import com.microservices.payment_service.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.secret}")
    private String secret;

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Map<String, Object> createRazorpayOrder(int amount) throws Exception {

        RazorpayClient client = new RazorpayClient(key, secret);

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // ✅ PAISE (FIX)
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = client.orders.create(options);

        Payment payment = new Payment();
        payment.setOrderId(order.get("id"));
        payment.setAmount(amount);
        payment.setStatus("CREATED");
        paymentRepository.save(payment);

        Map<String, Object> response = new HashMap<>();
        response.put("orderId", order.get("id"));
        response.put("amount", amount);

        return response;
    }


    @Override
    public boolean verifyPayment(Map<String, String> data) {
        try {
            String payload =
                    data.get("razorpay_order_id") + "|" +
                            data.get("razorpay_payment_id");

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));

            byte[] digest = mac.doFinal(payload.getBytes());
            String generatedSignature = bytesToHex(digest);

            boolean success = generatedSignature
                    .equals(data.get("razorpay_signature"));

            if (success) {
                Payment payment = paymentRepository
                        .findByOrderId(data.get("razorpay_order_id"));
                payment.setStatus("SUCCESS");
                paymentRepository.save(payment);
            }

            return success;

        } catch (Exception e) {
            return false;
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
