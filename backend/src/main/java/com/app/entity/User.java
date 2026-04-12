package com.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.HashSet; // MỚI THÊM VÀO
import java.util.Set; // MỚI THÊM VÀO

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// Có thể chuyển sang Class Student trong tương lai
    @Column(nullable = false, unique = true)
    private String studentId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    @Email(message = "Please provide a valid email address")
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany(mappedBy = "listStudents", fetch = FetchType.EAGER)
    private Set<Course> courses = new HashSet<>(); // Thêm khoá học trong học sinh

    // Default Constructor (Required by JPA)
    public User() {
    }

    // Constructor with fields
    public User(String studentId, String username, String password, String fullName, String email, UserRole role) {
        this.studentId = studentId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    // ================= MỚI THÊM VÀO NGÀY 4/7 =================
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    // ================= THÊM NGÀY 4/8 ===================
    public Course findCourseById(String courseId) {
        return courses.stream()
                .filter(course -> course.getCourseId().equalsIgnoreCase(courseId))
                .findFirst().orElse(null);
    }

    public Course findCourseByName(String name) {
        return courses.stream()
                .filter(course -> course.getCourseName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public int getCourseCount() {
        return courses.size();
    }

    // ================= THÊM NGÀY 4/11 ==================
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /// Có thể chuyển sang Class Student
    public String getStudentId() {
        return studentId;
    }

    /// Có thể chuyển sang Class Student
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}