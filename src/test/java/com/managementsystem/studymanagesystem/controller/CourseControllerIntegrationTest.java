package com.managementsystem.studymanagesystem.controller;

import com.managementsystem.studymanagesystem.dto.CourseCreateDto;
import com.managementsystem.studymanagesystem.dto.CourseDto;
import com.managementsystem.studymanagesystem.entity.Course;
import com.managementsystem.studymanagesystem.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseControllerIntegrationTest extends AbstractIntegrationTest {

    private Teacher teacher;
    private Course course;

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
        course = courseRepository.save(c);
    }

    @Test
    void getById_returnsCourse() {
        ResponseEntity<CourseDto> response =
                restTemplate.getForEntity(url("/courses/" + course.getId()), CourseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Java", response.getBody().courseName());
        assertEquals(teacher.getId(), response.getBody().teacherId());
    }

    @Test
    void create_savesCourse() {
        CourseCreateDto request = new CourseCreateDto("Spring", "Курс по Spring", teacher.getId());

        ResponseEntity<CourseDto> response =
                restTemplate.postForEntity(url("/courses"), request, CourseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, courseRepository.count());
    }

    @Test
    void create_unknownTeacher_returns404() {
        CourseCreateDto request = new CourseCreateDto("Spring", "Курс", 999999L);

        HttpClientErrorException ex = assertThrows(HttpClientErrorException.class,
                () -> restTemplate.postForEntity(url("/courses"), request, String.class));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void delete_removesCourse() {
        restTemplate.delete(url("/courses/" + course.getId()));

        assertTrue(courseRepository.findById(course.getId()).isEmpty());
    }
}