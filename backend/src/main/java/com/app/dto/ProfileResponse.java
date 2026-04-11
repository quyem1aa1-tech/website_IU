package com.app.dto;

import com.app.entity.UserRole;

public class ProfileResponse {
    private String status;
    private String message;
    private String username;
    private UserRole role;
    private String fullName;

    // Constructor
    public ProfileResponse(String status, String message, String username, UserRole role, String fullName) {
        this.status = status;
        this.message = message;
        this.username = username;
        this.role = role;
        this.fullName = fullName;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
