package com.managementsystem.studymanagesystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseCreateDto (
        @NotBlank(message = "Название курса обязательно") String courseName,
        String courseDescription,
        @NotNull(message = "ID преподавателя обязателен") Long teacherId
){
}
