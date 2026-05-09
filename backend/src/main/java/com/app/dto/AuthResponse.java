package com.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.app.entity.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String status;
    private String message;
    private String username;
    private UserRole role;
    private String fullName;

    public String getWelcomeMessage() {
        return "Welcome: " + this.fullName;
    }
}