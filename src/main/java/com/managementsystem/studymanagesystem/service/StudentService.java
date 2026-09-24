package com.managementsystem.studymanagesystem.service;

import com.managementsystem.studymanagesystem.dto.StudentCreateDto;
import com.managementsystem.studymanagesystem.dto.StudentDto;
import com.managementsystem.studymanagesystem.entity.Student;
import com.managementsystem.studymanagesystem.exception.EntityNotFoundException;
import com.managementsystem.studymanagesystem.mapper.StudentMapper;
import com.managementsystem.studymanagesystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public Page<StudentDto> getAll(Pageable pageable) {
        return studentRepository.findAll(pageable).map(studentMapper::toDto);
    }

    public StudentDto getById(Long id) {
        return studentMapper.toDto(findStudentOrThrow(id));
    }

    public StudentDto create(StudentCreateDto dto) {
        Student student = studentMapper.toEntity(dto);
        return studentMapper.toDto(studentRepository.save(student));
    }

    public StudentDto update(Long id, StudentCreateDto dto) {
        Student student = findStudentOrThrow(id);
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        return studentMapper.toDto(studentRepository.save(student));
    }

    public void delete(Long id) {
        studentRepository.delete(findStudentOrThrow(id));
    }

    public Student findStudentOrThrow(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Студент с id=" + id + " не найден"));
    }
}