package com.managementsystem.studymanagesystem.service;

import com.managementsystem.studymanagesystem.dto.TeacherCreateDto;
import com.managementsystem.studymanagesystem.dto.TeacherDto;
import com.managementsystem.studymanagesystem.entity.Teacher;
import com.managementsystem.studymanagesystem.exception.EntityNotFoundException;
import com.managementsystem.studymanagesystem.mapper.TeacherMapper;
import com.managementsystem.studymanagesystem.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public Page<TeacherDto> getAll(Pageable pageable) {
        return teacherRepository.findAll(pageable).map(teacherMapper::toDto);
    }

    public TeacherDto getById(Long id) {
        return teacherMapper.toDto(findTeacherOrThrow(id));
    }

    public TeacherDto create(TeacherCreateDto dto) {
        Teacher teacher = teacherMapper.toEntity(dto);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public TeacherDto update(Long id, TeacherCreateDto dto) {
        Teacher teacher = findTeacherOrThrow(id);
        teacher.setFirstName(dto.firstName());
        teacher.setLastName(dto.lastName());
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public void delete(Long id) {
        teacherRepository.delete(findTeacherOrThrow(id));
    }

    public Teacher findTeacherOrThrow(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Преподаватель с id=" + id + " не найден"));
    }
}