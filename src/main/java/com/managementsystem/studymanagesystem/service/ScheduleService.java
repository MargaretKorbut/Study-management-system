package com.managementsystem.studymanagesystem.service;

import com.managementsystem.studymanagesystem.dto.ScheduleCreateDto;
import com.managementsystem.studymanagesystem.dto.ScheduleDto;
import com.managementsystem.studymanagesystem.entity.Course;
import com.managementsystem.studymanagesystem.entity.Group;
import com.managementsystem.studymanagesystem.entity.Schedule;
import com.managementsystem.studymanagesystem.entity.Teacher;
import com.managementsystem.studymanagesystem.exception.EntityNotFoundException;
import com.managementsystem.studymanagesystem.mapper.ScheduleMapper;
import com.managementsystem.studymanagesystem.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final GroupService groupService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    public ScheduleDto getById(Long id) {
        return scheduleMapper.toDto(findScheduleOrThrow(id));
    }

    public ScheduleDto create(ScheduleCreateDto dto) {
        Group group = groupService.findGroupOrThrow(dto.groupId());
        Teacher teacher = teacherService.findTeacherOrThrow(dto.teacherId());
        Course course = courseService.findCourseOrThrow(dto.courseId());

        Schedule schedule = new Schedule();
        schedule.setStartDate(dto.startDate());
        schedule.setEndDate(dto.endDate());
        schedule.setGroup(group);
        schedule.setTeacher(teacher);
        schedule.setCourse(course);

        return scheduleMapper.toDto(scheduleRepository.save(schedule));
    }

    public ScheduleDto update(Long id, ScheduleCreateDto dto) {
        Schedule schedule = findScheduleOrThrow(id);
        schedule.setStartDate(dto.startDate());
        schedule.setEndDate(dto.endDate());
        schedule.setGroup(groupService.findGroupOrThrow(dto.groupId()));
        schedule.setTeacher(teacherService.findTeacherOrThrow(dto.teacherId()));
        schedule.setCourse(courseService.findCourseOrThrow(dto.courseId()));
        return scheduleMapper.toDto(scheduleRepository.save(schedule));
    }

    public void delete(Long id) {
        scheduleRepository.delete(findScheduleOrThrow(id));
    }

    public Page<ScheduleDto> getByGroup(Long groupId, Pageable pageable) {
        return scheduleRepository.findByGroupId(groupId, pageable).map(scheduleMapper::toDto);
    }

    public Page<ScheduleDto> getByTeacher(Long teacherId, Pageable pageable) {
        return scheduleRepository.findByTeacherId(teacherId, pageable).map(scheduleMapper::toDto);
    }

    public void deleteOlderThan(LocalDateTime threshold) {
        scheduleRepository.deleteByEndDateBefore(threshold);
    }

    public Schedule findScheduleOrThrow(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись расписания с id=" + id + " не найдена"));
    }
}