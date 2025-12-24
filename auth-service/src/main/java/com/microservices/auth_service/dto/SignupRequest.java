package com.microservices.auth_service.dto;

import com.microservices.auth_service.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SignupRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    /**
     * USER or RESTAURANT only
     * ADMIN must NOT be allowed from frontend
     */
    @NotNull(message = "Role is required")
    private Role role;

    // ---------- GETTERS & SETTERS ----------

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
