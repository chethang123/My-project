package com.microservices.auth_service.controller;

import com.microservices.auth_service.dto.AuthResponse;
import com.microservices.auth_service.dto.LoginRequest;
import com.microservices.auth_service.dto.SignupRequest;
import com.microservices.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    // ✅ SIGNUP WITH VALIDATION
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(
            @RequestBody @Valid SignupRequest req) {

        service.signup(req);
        return ResponseEntity.ok(
                Map.of("message", "Signup successful")
        );
    }


    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid LoginRequest req) {

        return ResponseEntity.ok(service.login(req));
    }

}
