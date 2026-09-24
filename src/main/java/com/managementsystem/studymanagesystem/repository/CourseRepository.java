package com.managementsystem.studymanagesystem.repository;

import com.managementsystem.studymanagesystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}