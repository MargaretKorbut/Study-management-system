package com.managementsystem.studymanagesystem.mapper;

import com.managementsystem.studymanagesystem.dto.ScheduleDto;
import com.managementsystem.studymanagesystem.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "course.id", target = "courseId")
    ScheduleDto toDto(Schedule schedule);
}