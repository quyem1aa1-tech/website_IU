package com.app.dto;

import com.app.entity.UserRole;

public class AuthResponse {
    private String status;
    private String message;
    private String username;
    private UserRole role;

    // Constructor
    public AuthResponse(String status, String message, String username, UserRole role) {
        this.status = status;
        this.message = message;
        this.username = username;
        this.role = role;
    }

    // Getters and Setters (Important for JSON conversion)
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }
}