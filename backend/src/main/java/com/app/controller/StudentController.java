package com.app.controller;

import com.app.entity.Course;
import com.app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * StudentController: Cổng tiếp nhận mọi yêu cầu liên quan đến nghiệp vụ Sinh viên.
 * Quản lý các luồng: Đăng ký môn học (Enrollment) và Tra cứu cá nhân.
 */
@RestController
@RequestMapping("/api/students") // Gốc (Root) của mọi API trong Controller này
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * API Đăng ký môn học (Many-to-Many Enrollment)
     * URL ví dụ: POST http://localhost:8080/api/students/1/enroll/5
     * * @param userId   ID của sinh viên thực hiện đăng ký
     * @param courseId ID của khóa học muốn tham gia
     * @return 200 OK nếu thành công | 400 Bad Request nếu logic nghiệp vụ bị lỗi (VD: trùng môn)
     */
    @PostMapping("/{userId}/enroll/{courseId}")
    public ResponseEntity<String> enrollInCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        // Ủy quyền xử lý logic cho Service để giữ Controller gọn gàng (Thin Controller)
        String result = studentService.enrollInCourse(userId, courseId);

        // Kiểm tra tiền tố để phản hồi HTTP Status phù hợp cho Frontend
        return result.startsWith("SUCCESS")
               ? ResponseEntity.ok(result)
               : ResponseEntity.badRequest().body(result);
    }

    /**
     * API Tra cứu danh sách môn học của một Sinh viên cụ thể
     * URL ví dụ: GET http://localhost:8080/api/students/1/courses
     * * @param userId ID sinh viên cần xem danh sách môn
     * @return Tập hợp (Set) các Course mà sinh viên này đang học dưới dạng JSON
     */
    @GetMapping("/{userId}/courses")
    public ResponseEntity<Set<Course>> getCoursesByStudent(@PathVariable Long userId) {
        // Controller đóng vai trò "Điều phối viên": Nhận ID -> Gọi Service -> Trả kết quả
        Set<Course> courses = studentService.getStudentCourses(userId);
        return ResponseEntity.ok(courses);
    }
}