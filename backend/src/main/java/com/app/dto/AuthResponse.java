package com.app.dto;

import com.app.entity.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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