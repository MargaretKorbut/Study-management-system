package com.managementsystem.studymanagesystem.controller;

import com.managementsystem.studymanagesystem.dto.ScheduleDto;
import com.managementsystem.studymanagesystem.entity.Course;
import com.managementsystem.studymanagesystem.entity.Group;
import com.managementsystem.studymanagesystem.entity.Schedule;
import com.managementsystem.studymanagesystem.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScheduleControllerIntegrationTest extends AbstractIntegrationTest {

    private Group group;
    private Teacher teacher;
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        Teacher t = new Teacher();
        t.setFirstName("Иван");
        t.setLastName("Петров");
        teacher = teacherRepository.save(t);

        Course c = new Course();
        c.setCourseName("Java");
        c.setCourseDescription("Базовый курс");
        c.setTeacher(teacher);
        Course course = courseRepository.save(c);

        Group g = new Group();
        g.setGroupName("Группа А");
        group = groupRepository.save(g);

        Schedule s = new Schedule();
        s.setStartDate(LocalDateTime.of(2026, 10, 20, 10, 0));
        s.setEndDate(LocalDateTime.of(2026, 10, 20, 12, 0));
        s.setGroup(group);
        s.setTeacher(teacher);
        s.setCourse(course);
        schedule = scheduleRepository.save(s);
    }

    @Test
    void getById_returnsSchedule() {
        ResponseEntity<ScheduleDto> response =
                restTemplate.getForEntity(url("/schedules/" + schedule.getId()), ScheduleDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(group.getId(), response.getBody().groupId());
    }

    @Test
    void getByGroup_returnsPage() {
        byte[] body = restTemplate.getForObject(
                url("/schedules/group/" + group.getId() + "?page=0&size=10"), byte[].class);

        assertTrue(new String(body, StandardCharsets.UTF_8).contains("\"groupId\":" + group.getId()));
    }

    @Test
    void getByTeacher_returnsPage() {
        byte[] body = restTemplate.getForObject(
                url("/schedules/teacher/" + teacher.getId() + "?page=0&size=10"), byte[].class);

        assertTrue(new String(body, StandardCharsets.UTF_8).contains("\"teacherId\":" + teacher.getId()));
    }
}