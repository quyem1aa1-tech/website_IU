package com.app.config;

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

    // 1. Khai báo thuật toán mã hóa mật khẩu (Cậu đã làm đúng)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Cấu hình "Bộ lọc bảo mật" (Security Filter Chain)
    // Note: Đây là nơi cậu quyết định link nào được vào, link nào bị chặn
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. TẮT CSRF - Đây là bước quan trọng nhất để Postman chạy được POST
                .csrf(csrf -> csrf.disable())

                // 2. Cấu hình CORS (nếu bạn chạy React/Angular sau này)
                .cors(cors -> cors.disable())

                // 3. Phân quyền
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Cho phép tất cả mà không cần login
                )

                // 4. Đảm bảo không dùng Form Login mặc định của Spring
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}