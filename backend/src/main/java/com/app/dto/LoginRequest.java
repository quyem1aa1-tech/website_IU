package com.app.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Login
@Data
@NoArgsConstructor // Tạo Constructor không đối số (Spring cần cái này)
@AllArgsConstructor // Tạo Constructor có đầy đủ đối số (Dùng để test cho nhanh)
public class LoginRequest {
    private String username;
    private String password;

    
}
