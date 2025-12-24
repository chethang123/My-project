package com.microservices.payment_service;

import com.microservices.payment_service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
//@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService)
    {
        this.paymentService = paymentService;
    }
    @PostMapping("/create")
    public Map<String, Object> createOrder(@RequestParam int amount) throws Exception {
        return paymentService.createRazorpayOrder(amount);
    }

    @PostMapping("/verify")
    public String verifyPayment(@RequestBody Map<String, String> data) {
        return paymentService.verifyPayment(data)
                ? "PAYMENT_SUCCESS"
                : "PAYMENT_FAILED";
    }
}