package com.managementsystem.studymanagesystem.dto;

import jakarta.validation.constraints.NotBlank;

public record TeacherCreateDto(
        @NotBlank(message = "Имя не может быть пустым")
        String firstName,

        @NotBlank(message = "Фамилия не может быть пустой")
        String lastName
) {
}