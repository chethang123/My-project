package com.microservices.payment_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;




@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public Map<String, Object> createOrder(@RequestBody PaymentRequest request) throws Exception {
        return paymentService.createOrder(
                request.getAmount(),
                request.getUserId(),
                request.getProductId(),
                request.getQuantity()
        );
    }
}
