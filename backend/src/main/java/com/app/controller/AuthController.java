package com.app.controller;

import com.app.dto.AuthResponse;
import com.app.dto.LoginRequest;
import com.app.dto.SignupRequest;
import com.app.service.AuthService;
import com.app.entity.LoginStatus;
import com.app.entity.User;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginStatus status = service.loginUser(request.getUsername(), request.getPassword());

        return switch (status) {
            case SUCCESS -> {
                // Take old information from user to return
                User user = service.getUserByUsername(request.getUsername()).get();
                yield ResponseEntity.ok(new AuthResponse("success", "Welcome to International University!",
                        user.getUsername(), user.getRole(), user.getFullName()));
            }

            case USER_NOT_FOUND -> ResponseEntity.status(404).body("Error: Username does not exist.");

            case WRONG_PASSWORD -> ResponseEntity.status(401).body("Error: Incorrect password. Please try again.");

            default -> ResponseEntity.status(500).body("An unknown error occurred.");
        };
    }

    /**
     * API Đăng ký tài khoản mới (User Registration)
     * URL: POST http://localhost:8080/api/auth/signup
     * * @param request Chứa thông tin đăng ký (username, password, fullname, role)
     * 
     * @return 200 OK nếu tạo tài khoản thành công | 501/400 nếu có lỗi nghiệp vụ
     *         hoặc hệ thống
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {
        try {
            // Bước 1: Gọi AuthService để thực hiện logic kiểm tra và lưu trữ
            // Đảm bảo không khởi tạo thủ công (new), sử dụng Dependency Injection
            String result = service.registerUser(request);

            // Bước 2: Trả về kết quả thành công cho người dùng
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            // Bước 3: Xử lý ngoại lệ (Exception Handling)
            // In lỗi ra Console để lập trình viên theo dõi (Debug)
            e.printStackTrace();

            // Trả về mã lỗi để Frontend biết hệ thống đang gặp vấn đề
            // Lưu ý: 501 thường dùng cho các tính năng chưa hoàn thiện,
            // có thể cân nhắc đổi thành 500 (Internal Server Error) sau này.
            return ResponseEntity.status(501).body("Error: " + e.getMessage());
        }
    }
}