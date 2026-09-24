package com.managementsystem.studymanagesystem.mapper;

import com.managementsystem.studymanagesystem.dto.StudentCreateDto;
import com.managementsystem.studymanagesystem.dto.StudentDto;
import com.managementsystem.studymanagesystem.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toDto(Student student);
    Student toEntity(StudentCreateDto dto);
}