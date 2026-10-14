package com.managementsystem.studymanagesystem.repository;

import com.managementsystem.studymanagesystem.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Page<Schedule> findByGroupId(Long groupId, Pageable pageable);
    Page<Schedule> findByTeacherId(Long teacherId, Pageable pageable);
    void deleteByEndDateBefore(LocalDateTime dateTime);
}