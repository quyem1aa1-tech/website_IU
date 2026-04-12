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
    CommandLineRunner initDatabase(UserRepository userRepository, CourseRepository courseRepository) {
        return args -> {
            // 1. Khởi tạo USER (Nếu trống)
            if (userRepository.count() == 0) {
                userRepository.save(new User("ITITIU25045", "VinhTruong@@", "123456",
                        "Truong The Vinh", "ITITIU25045@student.hcmiu.edu.vn", UserRole.STUDENT));
                userRepository.save(new User("ITITIU25044", "TranVinh", "123aa",
                        "Trinh Tran Vinh", "ttv7627@gmail.com", UserRole.STUDENT));
                System.out.println("✅ [Users] Created: VinhTruong@@ and TranVinh");
            }

            // 2. Khởi tạo COURSE (Nếu trống)
            if (courseRepository.count() == 0) {
                Course c1 = new Course("Object-Oriented Programming", "IT031IU");
                Course c2 = new Course("Data Structures and Algorithms", "IT123IU");
                Course c3 = new Course("Calculus 1", "MA018IU");
                Course c4 = new Course("Discrete Mathematics", "MA209IU");

                courseRepository.saveAll(List.of(c1, c2, c3, c4));
                System.out.println("📚 [Courses] Created 4 sample subjects!");

                // 3. Kịch bản: Tự động cho Vinh học 2 môn để test API GET
                userRepository.findByUsername("VinhTruong@@").ifPresent(vinh -> {
                    c1.addStudent(vinh); // Sử dụng hàm "bắt tay" 2 chiều
                    c2.addStudent(vinh);
                    courseRepository.save(c1);
                    courseRepository.save(c2);
                    System.out.println("🔗 [Test] Enrolled Vinh into OOP and DSA!");
                });
            }
        };
    }
}