package com.app;

import com.app.entity.Course; // Thêm import này
import com.app.entity.User;
import com.app.entity.UserRole;
import com.app.repository.CourseRepository; // Thêm import này
import com.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        logger.info("===============================================");
        logger.info("🚀 IU WEBSITE - INTERNAL SYSTEM IS READY!");
        logger.info("📍 Access URL: http://localhost:8080");
        logger.info("===============================================");
    }

    @Bean
    @Transactional
    CommandLineRunner initDatabase(UserRepository userRepository, CourseRepository courseRepository,
            PasswordEncoder passwordEncoder) { // Bổ sung PasswordEncoder vào đây {
        return args -> {
            // 1. Khởi tạo USER (Nếu trống)
            if (userRepository.count() == 0) {
                userRepository.save(new User("ITITIU25045", "VinhTruong@@", passwordEncoder.encode("123456"), // Mã hóa
                                                                                                              // mật
                                                                                                              // khẩu
                        "Truong The Vinh", "ITITIU25045@student.hcmiu.edu.vn", UserRole.STUDENT));

                userRepository.save(new User("ITITIU25044", "TranVinh", passwordEncoder.encode("123aa"), // Mã hóa mật
                                                                                                         // khẩu
                        "Trinh Tran Vinh", "ttv7627@gmail.com", UserRole.STUDENT));

                System.out.println("✅ [Users] Created: VinhTruong@@ and TranVinh (Passwords Encrypted with BCrypt!)");
            }
            // 2. Khởi tạo COURSE (Sạch sẽ, không trùng lặp)
            if (courseRepository.count() == 0) {
                // Tạo danh sách môn học duy nhất
                Course c1 = new Course("Object-Oriented Programming", "IT031IU");
                Course c2 = new Course("Data Structures and Algorithms", "IT123IU");
                Course c3 = new Course("Calculus 1", "MA018IU");
                Course c4 = new Course("Discrete Mathematics", "MA209IU");
                Course c5 = new Course("Introduction to Computing", "IT064IU");
                Course c6 = new Course("C/C++ Programming", "IT116IU");
                Course c7 = new Course("Philosophy of Marxism and Leninism", "PE015IU");
                Course c8 = new Course("Scientific socialism", "PE017IU");
                Course c9 = new Course("Linear Algebra", "MA033IU");
                Course c10 = new Course("Writing AE2", "EN011IU");

                // Lưu tất cả vào Database (Chỉ có 8 môn, không còn c9, c10 trùng lặp)
                courseRepository.saveAll(List.of(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10));
                System.out.println("📚 [Courses] Created 8 unique sample subjects!");

                // 3. Kịch bản: Tự động cho Vinh học 2 môn
                userRepository.findByUsername("VinhTruong@@").ifPresent(vinh -> {
                    c1.addStudent(vinh);
                    c2.addStudent(vinh);
                    courseRepository.save(c1);
                    courseRepository.save(c2);
                    System.out.println("🔗 [Test] Enrolled Vinh into OOP and DSA!");
                });
            }
        };
    }
}