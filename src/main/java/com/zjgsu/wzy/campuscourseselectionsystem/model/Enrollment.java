package com.zjgsu.wzy.campuscourseselectionsystem.model;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="enrollments")
public class Enrollment {
    @Id
    private String id;

    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String courseId;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
