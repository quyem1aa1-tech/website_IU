package com.app.repository;

import com.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // --- NHÓM 1: TÌM KIẾM (Trả về dữ liệu để sử dụng) ---
    
    // Dùng cho Login hoặc lấy thông tin User
    Optional<User> findByUsername(String username);

    // Dùng để tìm kiếm sinh viên theo MSSV (ví dụ: ITITIU25045)
    Optional<User> findByStudentId(String studentId);

    // Dùng cho tính năng "Quên mật khẩu"
    Optional<User> findByEmail(String email);


    // --- NHÓM 2: KIỂM TRA (Trả về true/false, dùng cho Signup) ---
    // Note: Những hàm này chạy cực nhanh vì chỉ thực hiện lệnh COUNT trong SQL
    
    // Kiểm tra trùng tên đăng nhập
    boolean existsByUsername(String username);

    // Kiểm tra trùng Email
    boolean existsByEmail(String email);

    // Kiểm tra trùng MSSV (Tránh việc một mã sinh viên đăng ký 2 tài khoản)
    boolean existsByStudentId(String studentId);


}