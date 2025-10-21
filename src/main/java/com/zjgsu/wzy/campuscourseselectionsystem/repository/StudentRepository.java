package com.zjgsu.wzy.campuscourseselectionsystem.repository;

import com.zjgsu.wzy.campuscourseselectionsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {

}
