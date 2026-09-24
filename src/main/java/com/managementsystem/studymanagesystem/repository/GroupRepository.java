package com.managementsystem.studymanagesystem.repository;

import com.managementsystem.studymanagesystem.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}