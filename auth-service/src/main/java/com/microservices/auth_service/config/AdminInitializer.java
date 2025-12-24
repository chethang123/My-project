package com.microservices.auth_service.config;

import com.microservices.auth_service.entity.User;
import com.microservices.auth_service.entity.Role;
import com.microservices.auth_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AdminInitializer(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {

        if (!repo.existsByRole(Role.ADMIN)) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin@123"));
            admin.setRole(Role.ADMIN);

            repo.save(admin);

            System.out.println("✅ ADMIN CREATED");
        }
    }
}
