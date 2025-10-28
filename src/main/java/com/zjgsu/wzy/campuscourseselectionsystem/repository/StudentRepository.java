package com.zjgsu.wzy.campuscourseselectionsystem.repository;

import com.zjgsu.wzy.campuscourseselectionsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    // 按学号查询
    Optional<Student> findByStudentId(String studentId);

    // 按邮箱查询
    Optional<Student> findByEmail(String email);

    // 判断学号是否存在
    boolean existsByStudentId(String studentId);

    // 判断邮箱是否存在
    boolean existsByEmail(String email);

    // 按专业筛选
    List<Student> findByMajor(String major);

    // 按年级筛选
    List<Student> findByGrade(Long grade);
}
