package com.zjgsu.wzy.campuscourseselectionsystem.service;


import com.zjgsu.wzy.campuscourseselectionsystem.model.Course;
import com.zjgsu.wzy.campuscourseselectionsystem.model.Enrollment;
import com.zjgsu.wzy.campuscourseselectionsystem.model.EnrollmentStatus;
import com.zjgsu.wzy.campuscourseselectionsystem.model.Student;
import com.zjgsu.wzy.campuscourseselectionsystem.repository.CourseRepository;
import com.zjgsu.wzy.campuscourseselectionsystem.repository.EnrollmentRepository;
import com.zjgsu.wzy.campuscourseselectionsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                            CourseRepository courseRepository,
                            StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * 学生选课
     */
    public Enrollment enroll(String studentId, String courseId) {
        // 1. 检查学生是否存在
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("学生不存在: " + studentId));

        // 2. 检查课程是否存在
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("课程不存在: " + courseId));

        // 3. 检查是否已经选过该课程（活跃状态）
        if (enrollmentRepository.existsActiveEnrollment(studentId, courseId)) {
            throw new IllegalStateException("学生已经选过该课程");
        }

        // 4. 检查课程容量
        long currentEnrolled = enrollmentRepository.countActiveByCourseId(courseId);
        if (currentEnrolled >= course.getCapacity()) {
            throw new IllegalStateException("课程已满，无法选课");
        }

        // 5. 创建选课记录
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // 6. 更新课程已选人数
        course.setEnrolledCount(currentEnrolled + 1);
        courseRepository.save(course);

        return savedEnrollment;
    }

    /**
     * 退课
     */
    public boolean drop(String enrollmentId) {
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findById(enrollmentId);
        if (enrollmentOpt.isEmpty()) {
            return false;
        }

        Enrollment enrollment = enrollmentOpt.get();

        // 只能退选活跃状态的课程
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new IllegalStateException("该选课记录不是活跃状态，无法退课");
        }

        // 更新状态为已退课
        enrollment.setStatus(EnrollmentStatus.DROPPED);
        enrollmentRepository.save(enrollment);

        // 更新课程已选人数
        Course course = courseRepository.findById(enrollment.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("课程不存在"));
        course.setEnrolledCount(Math.max(0, course.getEnrolledCount() - 1));
        courseRepository.save(course);

        return true;
    }

    @Transactional(readOnly = true)
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Enrollment> findByCourseId(String courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public List<Enrollment> findByStudentId(String studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
}
