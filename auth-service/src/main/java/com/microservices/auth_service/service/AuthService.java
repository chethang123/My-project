package com.microservices.auth_service.service;

import com.microservices.auth_service.dto.AuthResponse;
import com.microservices.auth_service.dto.LoginRequest;
import com.microservices.auth_service.dto.SignupRequest;
import com.microservices.auth_service.entity.Role;
import com.microservices.auth_service.entity.User;
import com.microservices.auth_service.repository.UserRepository;
import com.microservices.auth_service.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository repo,
                       JwtUtil jwtUtil,
                       PasswordEncoder encoder) {
        this.repo = repo;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    // ✅ SIGNUP
    public void signup(SignupRequest req) {

        // ❌ Duplicate username
        if (repo.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // ❌ Only ONE ADMIN allowed
        if (req.getRole() == Role.ADMIN && repo.existsByRole(Role.ADMIN)) {
            throw new RuntimeException("Admin already exists");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(req.getRole());

        repo.save(user);
    }

    // ✅ LOGIN
    public AuthResponse login(LoginRequest req) {

        User user = repo.findByUsername(req.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return new AuthResponse(token, user.getRole().name());
    }
}
