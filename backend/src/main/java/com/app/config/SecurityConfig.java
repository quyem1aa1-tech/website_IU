package com.app.config; // Thay đổi package cho đúng với cấu trúc dự án của bạn

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Khai báo "Máy băm mật khẩu" để dùng ở mọi nơi trong dự án
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Cấu hình phân quyền truy cập
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Tắt CSRF để có thể test API qua Postman/Browser dễ dàng
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Tạm thời cho phép tất cả các link để bạn tập trung làm logic
            )
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Hỗ trợ nếu bạn dùng H2 Console (tùy chọn)

        return http.build();
    }
}