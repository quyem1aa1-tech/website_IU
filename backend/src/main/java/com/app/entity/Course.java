package com.app.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String courseName;

    @Column(nullable = false, unique = true)
    private String courseId;

    @ManyToMany()
    @JoinTable(name = "course_student", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> listStudents = new HashSet<>();

    public Course() {
    }

    public Course(String courseName, String courseId) {
        this.courseName = courseName;
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // Đã đổi tên hàm cho chuẩn
    public Set<User> getlistStudents() {
        return listStudents;
    }

    public void setlistStudents(Set<User> listStudents) {
        this.listStudents = listStudents;
    }

    public int getStudentCount() {
        if (this.listStudents == null) {
            return 0;
        }
        return this.listStudents.size();
    }

    // Công cụ giúp học sinh
    public void addStudent(User user) {
        this.listStudents.add(user);
        user.getCourses().add(this);
    }

    public void removeStudent(User user) {
        this.listStudents.remove(user);
        user.getCourses().remove(this);
    }

    // ================ Override Hàm để dùng Set<> ==================
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id); // Compare by ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}