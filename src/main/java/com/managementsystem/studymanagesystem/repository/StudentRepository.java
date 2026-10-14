package com.managementsystem.studymanagesystem.repository;

import com.managementsystem.studymanagesystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
