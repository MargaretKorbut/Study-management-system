package com.managementsystem.studymanagesystem.controller;

import com.managementsystem.studymanagesystem.dto.StudentCreateDto;
import com.managementsystem.studymanagesystem.dto.StudentDto;
import com.managementsystem.studymanagesystem.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentControllerIntegrationTest extends AbstractIntegrationTest {

    private Student student;

    @BeforeEach
    void setUp() {
        Student s = new Student();
        s.setFirstName("Мария");
        s.setLastName("Сидорова");
        student = studentRepository.save(s);
    }

    @Test
    void getById_returnsStudent() {
        ResponseEntity<StudentDto> response =
                restTemplate.getForEntity(url("/students/" + student.getId()), StudentDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Мария", response.getBody().firstName());
    }

    @Test
    void create_savesStudent() {
        ResponseEntity<StudentDto> response = restTemplate.postForEntity(
                url("/students"), new StudentCreateDto("Олег", "Орлов"), StudentDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, studentRepository.count());
    }

    @Test
    void delete_removesStudent() {
        restTemplate.delete(url("/students/" + student.getId()));

        assertTrue(studentRepository.findById(student.getId()).isEmpty());
    }
}