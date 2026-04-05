package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class DemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        
        SpringApplication.run(DemoApplication.class, args);
        
        logger.info("===============================================");
        logger.info("🚀 WEBSITE IU - HỆ THỐNG NỘI BỘ ĐÃ SẴN SÀNG!");
        logger.info("📍 Truy cập tại: http://localhost:8080/api");
        logger.info("👥 Phân quyền: Học sinh & Giáo viên");
        logger.info("===============================================");
    }
}