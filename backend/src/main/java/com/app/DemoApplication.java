package com.app;

// Spring Boot Core Imports
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// Logging Imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Project Specific Imports (Của chính bạn tạo ra)
import com.app.entity.User;
import com.app.entity.UserRole;
import com.app.repository.UserRepository;
import com.app.entity.UserRole;
import com.app.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class DemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        // Launch the Spring Boot application
        SpringApplication.run(DemoApplication.class, args);

        logger.info("===============================================");

        logger.info("🚀 IU WEBSITE - INTERNAL SYSTEM IS READY!");

        logger.info("📍 Access URL: http://localhost:8080/api");

        logger.info("👥 Roles: Student & Teacher");

        logger.info("===============================================");
    }

    // Add new object in application

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new User("student1", "123", "Vinh Student", UserRole.STUDENT));
                repository.save(new User("teacher1", "123", "Dr. Tran Vinh", UserRole.TEACHER));
                System.out.println("Test users created: student1/123 and teacher1/123");
            }
        };
    }
}