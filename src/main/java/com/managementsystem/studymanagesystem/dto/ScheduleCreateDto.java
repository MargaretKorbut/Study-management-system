package com.managementsystem.studymanagesystem.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ScheduleCreateDto(
        @NotNull LocalDateTime startDate,
        @NotNull LocalDateTime endDate,
        @NotNull Long groupId,
        @NotNull Long teacherId,
        @NotNull Long courseId
) {

}
