package com.managementsystem.studymanagesystem.mapper;

import com.managementsystem.studymanagesystem.dto.GroupCreateDto;
import com.managementsystem.studymanagesystem.dto.GroupDto;
import com.managementsystem.studymanagesystem.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDto toDto(Group group);
    Group toEntity(GroupCreateDto dto);
}