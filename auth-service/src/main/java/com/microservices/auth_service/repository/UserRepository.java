package com.microservices.auth_service.repository;

import com.microservices.auth_service.entity.Role;
import com.microservices.auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 🔍 Find by username (login / signup)
    Optional<User> findByUsername(String username);

    // 🔍 Check if a role exists (ADMIN uniqueness)
    boolean existsByRole(Role role);

    // 🔍 Fetch user by role (used in AdminInitializer)
    Optional<User> findByRole(Role role);
}
