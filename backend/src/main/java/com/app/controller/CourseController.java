package com.app.controller;

import java.util.List;

import com.app.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Course;

/**
 * CourseController: Cửa ngõ công khai để tra cứu thông tin về các Khóa học.
 * Đối tượng sử dụng: Mọi người dùng (Sinh viên xem môn để đăng ký, Guest xem thông tin).
 */
@RestController
@RequestMapping("/api/courses") // Root API dành riêng cho tài nguyên Khóa học
public class CourseController {

    @Autowired
    private CourseService courseService; // Tạm thời dùng chung Service để quản lý logic tập trung

    /**
     * API Lấy toàn bộ danh sách khóa học hiện có trong Database.
     * URL: GET http://localhost:8080/api/courses
     *
     * * @return Danh sách JSON chứa tất cả các môn học (ID, tên môn,...)
     */
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        // Gọi hàm getAllCourses từ Service để lấy dữ liệu từ Repository
        // Trả về mã 200 OK kèm danh sách môn học
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    /**
     * API Tìm khóa học hiện có trong Database.
     * URL: GET http://localhost:8080/api/courses
     *
     * * @return Danh sách JSON chứa tất cả các môn học (ID, tên môn,...)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCourses(
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String courseId
    ) {
        List<Course> filtered = courseService.searchCourses(courseName, courseId);
        return ResponseEntity.ok(filtered);
    }
}

