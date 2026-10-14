package com.managementsystem.studymanagesystem.dto;

import jakarta.validation.constraints.NotBlank;

public record GroupCreateDto(
        @NotBlank(message = "Название группы обязательно") String groupName
) {}