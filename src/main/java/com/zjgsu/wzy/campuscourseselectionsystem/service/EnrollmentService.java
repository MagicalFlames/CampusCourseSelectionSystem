package com.zjgsu.wzy.campuscourseselectionsystem.service;


import com.zjgsu.wzy.campuscourseselectionsystem.model.Course;
import com.zjgsu.wzy.campuscourseselectionsystem.model.Enrollment;
import com.zjgsu.wzy.campuscourseselectionsystem.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    public EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public boolean delete(String enrollmentId) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(enrollmentId);
        if (enrollment.isPresent()) {
            enrollmentRepository.delete(enrollment.get());
            return true;
        }
        else {
            return false;
        }
    }

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> findByCourseId(String courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }
    public List<Enrollment> findByStudentId(String studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
}
