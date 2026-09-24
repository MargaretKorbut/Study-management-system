package com.managementsystem.studymanagesystem.dto;

import java.time.LocalDateTime;

public record ScheduleDto(
        Long id,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long groupId,
        Long teacherId,
        Long courseId
) {

}
