package com.managementsystem.studymanagesystem.service;

import com.managementsystem.studymanagesystem.dto.CourseCreateDto;
import com.managementsystem.studymanagesystem.dto.CourseDto;
import com.managementsystem.studymanagesystem.entity.Course;
import com.managementsystem.studymanagesystem.entity.Teacher;
import com.managementsystem.studymanagesystem.exception.EntityNotFoundException;
import com.managementsystem.studymanagesystem.mapper.CourseMapper;
import com.managementsystem.studymanagesystem.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final TeacherService teacherService;

    public Page<CourseDto> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable).map(courseMapper::toDto);
    }

    public CourseDto getById(Long id) {
        return courseMapper.toDto(findCourseOrThrow(id));
    }

    public CourseDto create(CourseCreateDto dto) {
        Teacher teacher = teacherService.findTeacherOrThrow(dto.teacherId());
        Course course = new Course();
        course.setCourseName(dto.courseName());
        course.setCourseDescription(dto.courseDescription());
        course.setTeacher(teacher);
        return courseMapper.toDto(courseRepository.save(course));
    }

    public CourseDto update(Long id, CourseCreateDto dto) {
        Course course = findCourseOrThrow(id);
        Teacher teacher = teacherService.findTeacherOrThrow(dto.teacherId());
        course.setCourseName(dto.courseName());
        course.setCourseDescription(dto.courseDescription());
        course.setTeacher(teacher);
        return courseMapper.toDto(courseRepository.save(course));
    }

    public void delete(Long id) {
        courseRepository.delete(findCourseOrThrow(id));
    }

    public Course findCourseOrThrow(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Курс с id=" + id + " не найден"));
    }
}