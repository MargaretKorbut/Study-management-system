package com.managementsystem.studymanagesystem.mapper;

import com.managementsystem.studymanagesystem.dto.TeacherCreateDto;
import com.managementsystem.studymanagesystem.dto.TeacherDto;
import com.managementsystem.studymanagesystem.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherDto toDto(Teacher teacher);
    Teacher toEntity(TeacherCreateDto dto);
}