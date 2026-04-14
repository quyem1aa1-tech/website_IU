package com.app.controller;

import com.app.dto.LoginRequest;
import com.app.dto.SignupRequest;
import com.app.service.AuthService;
import com.app.entity.LoginStatus;

import jakarta.validation.Valid;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private AuthService authService;

    /**
     * API Đăng nhập tài khoản (User Login)
     * URL: POST http://localhost:8080/api/auth/login
     * * @param request Chứa thông tin đăng nhập (username, password)
     *
     * @return 200 OK + Trả về "Welcome to ..." nếu tạo tài khoản thành công |
     *         501/400 nếu có lỗi nghiệp vụ
     *         hoặc hệ thống
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginData) {
        System.out.println("Full request object: " + loginData.toString());
        // Log để kiểm tra xem dữ liệu đã vào đến Controller chưa
        System.out.println("Controller received request for user: " + loginData.getUsername());

        // Gọi sang hàm Service mà bạn đã có sẵn
        try {
            LoginStatus status = authService.loginUser(
                    loginData.getUsername(),
                    loginData.getPassword()
            );

            // Trả về kết quả cho phía Client
            return ResponseEntity.ok(status);
        }
        catch (Exception e) {
            System.err.println("ERROR: Signup failed due to: " + e.getMessage());
            e.printStackTrace();

            // Trả về mã lỗi 500 (Server Error) kèm thông tin lỗi cho người dùng
            return ResponseEntity.status(400).body("Server Error: " + e.getMessage());
        }
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
            // Bước 1: Ghi log khi bắt đầu nhận yêu cầu
            System.out.println("LOG: Processing signup request for user: " + request.getUsername());

            /**
             * Bước 2: Gọi tầng Service để xử lý logic (Kiểm tra trùng, mã hóa pass, lưu DB)
             * Note: Bạn phải đảm bảo hàm registerUser trả về một chuỗi (String)
             * chứ không được trả về null hoặc void để tránh lỗi trắng màn hình.
             */
            String result = service.registerUser(request);

            // Bước 3: Kiểm tra nếu kết quả trả về bị trống
            if (result == null || result.isEmpty()) {
                System.out.println("WARNING: Service returned an empty result!");
                return ResponseEntity.ok("Registration successful, but no message returned.");
            }

            // Bước 4: Trả về kết quả thành công cho người dùng (Frontend sẽ thấy dòng này)
            System.out.println("SUCCESS: User registered successfully: " + request.getUsername());
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            // Bước 5: Xử lý khi có lỗi (Ví dụ: Trùng username, lỗi kết nối Database)
            // Note: In lỗi chi tiết ra console để bạn "debug"
            System.err.println("ERROR: Signup failed due to: " + e.getMessage());
            e.printStackTrace();

            // Trả về mã lỗi 500 (Server Error) kèm thông tin lỗi cho người dùng
            return ResponseEntity.status(500).body("Server Error: " + e.getMessage());
        }
    }

    /**
     * API Quên mật khẩu (Forgot Password)
     * URL: POST http://localhost:8080/api/auth/forgot-password
     * * @param request Chứa email
     *
     * @return 200 OK nếu nhập đúng email | 401 nếu có lỗi nghiệp vụ
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> payload) {
        // Ghi chú: Bắt đầu quá trình Debug để truy tìm nguyên nhân lỗi 500
        System.out.println("--- DEBUG START: forgotPassword API ---");

        try {
            // Ghi chú: Kiểm tra xem payload nhận được từ Thunder Client có gì
            System.out.println("Payload received: " + payload);

            // Bước 1: Trích xuất email từ JSON Map
            // Ghi chú: Phải đảm bảo key 'email' viết thường hoàn toàn
            String email = payload.get("email");
            System.out.println("Extracted email: " + email);

            // Bước 2: Kiểm tra dữ liệu đầu vào trước khi gọi Service
            // Ghi chú: Nếu email null ở đây, lỗi có thể do JSON gửi lên sai Key
            if (email == null || email.trim().isEmpty()) {
                System.err.println("CRITICAL: Email is null or empty!");
                return ResponseEntity.badRequest().body("Error: Email key is missing in JSON body.");
            }

            // Bước 3: Gọi Service để xử lý logic
            // Ghi chú: Nếu bị lỗi 500 ở dòng này, hãy check xem đã có @Autowired
            // AuthService chưa
            System.out.println("Calling AuthService.processForgotPassword...");
            String tempPassword = service.processForgotPassword(email);

            // Bước 4: Kiểm tra kết quả trả về từ Service
            if (tempPassword != null) {
                System.out.println("SUCCESS: Password generated and saved to DB.");
                return ResponseEntity.ok("New password created: " + tempPassword);
            } else {
                System.out.println("FAILURE: Email not found in Database.");
                return ResponseEntity.status(404).body("Error: Email address does not exist.");
            }

        } catch (NullPointerException e) {
            // Ghi chú: Lỗi này thường do service hoặc passwordEncoder bị null (quên
            // @Autowired)
            System.err.println("CATCHED: NullPointerException - Check your @Autowired dependencies!");
            e.printStackTrace(); // In toàn bộ dấu vết lỗi ra Console
            return ResponseEntity.status(500).body("Internal Error: Null reference detected. " + e.getMessage());

        } catch (Exception e) {
            // Ghi chú: Bắt tất cả các loại lỗi khác (Lỗi DB, Lỗi logic...)
            System.err.println("CATCHED: Unknown Exception occurred!");
            System.err.println("Exception type: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server Error: " + e.toString());
        } finally {
            System.out.println("--- DEBUG END ---");
        }
    }
}