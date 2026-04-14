package com.app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import com.app.entity.UserRole;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "Student id cannot be blank")
    private String studentId;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    // Sửa lại ở đây
    @NotBlank(message = "Password cannot be blank")
    private String password;

    // Và ở đây
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Role is required")
    private UserRole role; // e.g., "STUDENT" or "TEACHER"
}
