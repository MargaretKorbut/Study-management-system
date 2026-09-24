package com.managementsystem.studymanagesystem.repository;

import com.managementsystem.studymanagesystem.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}