package com.app.service;

import com.app.dto.ProfileResponse;
import com.app.entity.Course;
import com.app.entity.User;
import com.app.repository.CourseRepository;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Thực hiện đăng ký môn học cho sinh viên.
     * 
     * @param userId   ID của người dùng (Student)
     * @param courseId ID của khóa học cần đăng ký
     * @return Thông báo trạng thái (SUCCESS/ERROR)
     * @throws RuntimeException nếu không tìm thấy User hoặc Course
     */
    @Transactional // Đảm bảo tính Atomic: Hoặc lưu cả 2 bên thành công, hoặc không lưu gì cả để
                   // tránh rác dữ liệu
    public String enrollInCourse(Long userId, String courseId) {

        // 1. Kiểm tra sự tồn tại của thực thể trong Database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("ERROR: This ID student does not exist: " + userId));

        Course course = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new RuntimeException("ERROR: Student course ID not found. " + courseId));

        // 2. Kiểm tra nghiệp vụ (Business Logic): Tránh đăng ký trùng lặp
        // Sử dụng hàm contains() của Set để đạt hiệu năng O(1)
        if (user.getCourses().contains(course)) {
            return "ERROR: Bạn đã đăng ký môn học này từ trước!";
        }

        // 3. Thực hiện "Bắt tay" 2 chiều (Bidirectional Mapping)
        // Gọi hàm addStudent bên class Course để tự động cập nhật danh sách ở cả 2
        // Entity
        course.addStudent(user);

        // 4. Lưu lại phía "Chủ" (Owner Side) - Bảng trung gian course_student sẽ được
        // cập nhật
        courseRepository.save(course);

        return "SUCCESS: Đăng ký thành công môn " + course.getCourseName();
    }

    @Transactional
    public String dropCourse(Long userId, String courseId) {

        // 1. Fetch the student and the course
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("ERROR: This ID student does not exist: " + userId));

        if (user.findCourseById(courseId) == null) {
            throw new RuntimeException("ERROR: Student course ID not found. " + courseId);
        }
        Course course = user.findCourseById(courseId);

        // 2. Remove the course from the student's Set
        // This automatically handles the "Join Table" update in JPA
        course.removeStudent(user);

        // 3. Save (the relationship is updated)
        courseRepository.save(course);

        return "SUCCESS: DELETED COURSES" + course.getCourseName();
    }

    /**
     * Lấy danh sách các khóa học mà một sinh viên đang tham gia.
     */
    public Set<Course> getStudentCourses(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getCourses();
    }

    /**
     * Trả về thông tin cá nhân sinh viên có thể xem
     */
    public ProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("ERROR: This ID student does not exist: " + userId));

        return new ProfileResponse(user.getStudentId(), user.getUsername(), user.getFullName(), user.getEmail());
    }
}