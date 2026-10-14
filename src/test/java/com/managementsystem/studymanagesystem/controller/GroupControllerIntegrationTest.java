package com.managementsystem.studymanagesystem.controller;

import com.managementsystem.studymanagesystem.dto.GroupCreateDto;
import com.managementsystem.studymanagesystem.dto.GroupDto;
import com.managementsystem.studymanagesystem.entity.Group;
import com.managementsystem.studymanagesystem.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupControllerIntegrationTest extends AbstractIntegrationTest {

    private Group group;
    private Student student;

    @BeforeEach
    void setUp() {
        Group g = new Group();
        g.setGroupName("Группа А");
        group = groupRepository.save(g);

        Student s = new Student();
        s.setFirstName("Мария");
        s.setLastName("Сидорова");
        student = studentRepository.save(s);
    }

    @Test
    void getById_returnsGroup() {
        ResponseEntity<GroupDto> response =
                restTemplate.getForEntity(url("/groups/" + group.getId()), GroupDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Группа А", response.getBody().groupName());
    }

    @Test
    void create_savesGroup() {
        ResponseEntity<GroupDto> response = restTemplate.postForEntity(
                url("/groups"), new GroupCreateDto("Группа Б"), GroupDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, groupRepository.count());
    }

    @Test
    void addStudent_returnsOk() {
        ResponseEntity<GroupDto> response = restTemplate.postForEntity(
                url("/groups/" + group.getId() + "/students/" + student.getId()), null, GroupDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}