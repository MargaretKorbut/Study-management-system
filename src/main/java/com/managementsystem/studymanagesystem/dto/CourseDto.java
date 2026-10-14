package com.managementsystem.studymanagesystem.dto;

public record CourseDto(
        Long id,
        String courseName,
        String courseDescription,
        Long teacherId
){

}