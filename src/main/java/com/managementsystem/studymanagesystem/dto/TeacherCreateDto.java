package com.managementsystem.studymanagesystem.dto;

import jakarta.validation.constraints.NotBlank;

public record TeacherCreateDto(
        @NotBlank(message = "Имя обязательно")
        String firstName,

        @NotBlank(message = "Фамилия обязательна")
        String lastName
) {
}