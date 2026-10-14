package com.managementsystem.studymanagesystem.controller;

import com.managementsystem.studymanagesystem.dto.TeacherCreateDto;
import com.managementsystem.studymanagesystem.dto.TeacherDto;
import com.managementsystem.studymanagesystem.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TeacherControllerIntegrationTest extends AbstractIntegrationTest {

    private Teacher savedTeacher;

    @BeforeEach
    void setUp() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Иван");
        teacher.setLastName("Петров");
        savedTeacher = teacherRepository.save(teacher);
    }

    @Test
    void getById_returnsTeacher() {
        ResponseEntity<TeacherDto> response =
                restTemplate.getForEntity(url("/teachers/" + savedTeacher.getId()), TeacherDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Иван", response.getBody().firstName());
        assertEquals("Петров", response.getBody().lastName());
    }

    @Test
    void getById_notFound_returns404() {
        HttpClientErrorException ex = assertThrows(HttpClientErrorException.class,
                () -> restTemplate.getForEntity(url("/teachers/999999"), String.class));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void create_savesTeacher() {
        ResponseEntity<TeacherDto> response = restTemplate.postForEntity(
                url("/teachers"), new TeacherCreateDto("Мария", "Сидорова"), TeacherDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().id());
        assertEquals("Мария", response.getBody().firstName());
        assertEquals(2, teacherRepository.count());
    }

    @Test
    void update_changesTeacher() {
        restTemplate.put(url("/teachers/" + savedTeacher.getId()), new TeacherCreateDto("Пётр", "Иванов"));

        Teacher updated = teacherRepository.findById(savedTeacher.getId()).orElseThrow();
        assertEquals("Пётр", updated.getFirstName());
        assertEquals("Иванов", updated.getLastName());
    }

    @Test
    void delete_removesTeacher() {
        restTemplate.delete(url("/teachers/" + savedTeacher.getId()));

        assertTrue(teacherRepository.findById(savedTeacher.getId()).isEmpty());
    }

    @Test
    void getAll_returnsPage() {
        byte[] body = restTemplate.getForObject(url("/teachers?page=0&size=10"), byte[].class);

        assertTrue(new String(body, StandardCharsets.UTF_8).contains("Петров"));
    }
}