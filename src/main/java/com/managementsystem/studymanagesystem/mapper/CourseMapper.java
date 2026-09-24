package com.managementsystem.studymanagesystem.mapper;

import com.managementsystem.studymanagesystem.dto.CourseDto;
import com.managementsystem.studymanagesystem.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source = "teacher.id", target = "teacherId")
    CourseDto toDto(Course course);
}