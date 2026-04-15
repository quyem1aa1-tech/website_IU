
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
            if (status == LoginStatus.SUCCESS) {
                return ResponseEntity.ok(status);
            } else {
                return ResponseEntity.status(400).build();
            }
        }
        catch (Exception e) {
            System.err.println("ERROR: Signup failed due to: " + e.getMessage());
            e.printStackTrace();

            // Trả về mã lỗi 500 (Server Error) kèm thông tin lỗi cho người dùng
            return ResponseEntity.status(500).body("Server Error: " + e.getMessage());
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

    /**
     * API Đổi mật khẩu (Reset Password)
     * URL: POST http://localhost:8080/api/auth/reset-password
     * * @param request Chứa email, oldPassword, newPassword, confirmPassword
     *
     * @return 200 OK nếu nhập đúng | 401 nếu có lỗi nghiệp vụ
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> newPasswordForm) {
        /// Get Object -> variables
        String email = newPasswordForm.get("email");
        String oldPass = newPasswordForm.get("oldPassword");
        String newPass = newPasswordForm.get("newPassword");
        String confirmPass = newPasswordForm.get("confirmPassword");

        /// Gọi hàm resetPassword()
        try {
            String result = authService.resetPassword(email, oldPass, newPass, confirmPass);

            // SUCCESS
            if (result.startsWith("[200]")) {
                return ResponseEntity.ok(result);
            }
            // Mật khẩu cũ SAI
            else if (result.startsWith("[401]")) {
                return ResponseEntity.status(401).body(result);
            }
            // Mật khẩu mới SAI định dạng
            else if (result.startsWith("[403]")) {
                return ResponseEntity.status(403).body(result);
            }
            // Xác nhận lại mật khẩu SAI
            else if (result.startsWith("[405]")) {
                return ResponseEntity.status(405).body(result);
            }
            else {
                return ResponseEntity.status(404).body("UNKNOWN ERROR!");
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body("Error: " + e.toString());
        }
    }
}