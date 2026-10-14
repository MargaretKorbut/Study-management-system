package com.managementsystem.studymanagesystem.service;

import com.managementsystem.studymanagesystem.dto.GroupCreateDto;
import com.managementsystem.studymanagesystem.dto.GroupDto;
import com.managementsystem.studymanagesystem.entity.Group;
import com.managementsystem.studymanagesystem.entity.Student;
import com.managementsystem.studymanagesystem.exception.EntityNotFoundException;
import com.managementsystem.studymanagesystem.mapper.GroupMapper;
import com.managementsystem.studymanagesystem.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final StudentService studentService;

    public Page<GroupDto> getAll(Pageable pageable) {
        return groupRepository.findAll(pageable).map(groupMapper::toDto);
    }

    public GroupDto getById(Long id) {
        return groupMapper.toDto(findGroupOrThrow(id));
    }

    @Transactional
    public GroupDto create(GroupCreateDto dto) {
        Group group = groupMapper.toEntity(dto);
        return groupMapper.toDto(groupRepository.save(group));
    }

    @Transactional
    public GroupDto update(Long id, GroupCreateDto dto) {
        Group group = findGroupOrThrow(id);
        group.setGroupName(dto.groupName());
        return groupMapper.toDto(groupRepository.save(group));
    }

    @Transactional
    public void delete(Long id) {
        groupRepository.delete(findGroupOrThrow(id));
    }

    public GroupDto addStudent(Long groupId, Long studentId) {
        Group group = findGroupOrThrow(groupId);
        Student student = studentService.findStudentOrThrow(studentId);
        group.getStudents().add(student);
        return groupMapper.toDto(groupRepository.save(group));
    }

    public Group findGroupOrThrow(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Группа с id=" + id + " не найдена"));
    }
}