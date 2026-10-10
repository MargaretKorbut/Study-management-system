package com.managementsystem.studymanagesystem.controller;

import com.managementsystem.studymanagesystem.dto.TeacherCreateDto;
import com.managementsystem.studymanagesystem.dto.TeacherDto;
import com.managementsystem.studymanagesystem.entity.Teacher;
import com.managementsystem.studymanagesystem.repository.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class TeacherControllerIntegrationTest {

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TeacherRepository teacherRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private Teacher savedTeacher;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/v1/teachers";
    }

    @BeforeEach
    void setUp() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Мария");
        teacher.setLastName("Маринова");
        savedTeacher = teacherRepository.save(teacher);
    }

    @AfterEach
    void tearDown() {
        teacherRepository.deleteAll();
    }

    @Test
    void getById_returnsTeacher() {
        ResponseEntity<TeacherDto> response =
                restTemplate.getForEntity(baseUrl() + "/" + savedTeacher.getId(), TeacherDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Мария", response.getBody().firstName());
        assertEquals("Мариновна", response.getBody().lastName());
    }

    @Test
    void getById_notFound_returns404() {
        HttpClientErrorException ex = assertThrows(HttpClientErrorException.class,
                () -> restTemplate.getForEntity(baseUrl() + "/999999", String.class));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void create_savesTeacher() {
        TeacherCreateDto request = new TeacherCreateDto("Ульяна", "Ульяновна");

        ResponseEntity<TeacherDto> response =
                restTemplate.postForEntity(baseUrl(), request, TeacherDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().id());
        assertEquals("Ульяна", response.getBody().firstName());
        assertEquals(2, teacherRepository.count());
    }

    @Test
    void update_changesTeacher() {
        TeacherCreateDto request = new TeacherCreateDto("Геннадий", "Геннадин");

        restTemplate.put(baseUrl() + "/" + savedTeacher.getId(), request);

        Teacher updated = teacherRepository.findById(savedTeacher.getId()).orElseThrow();
        assertEquals("Геннадий", updated.getFirstName());
        assertEquals("Геннадин", updated.getLastName());
    }

    @Test
    void delete_removesTeacher() {
        restTemplate.delete(baseUrl() + "/" + savedTeacher.getId());

        assertTrue(teacherRepository.findById(savedTeacher.getId()).isEmpty());
    }

    @Test
    void getAll_returnsPage() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(baseUrl() + "?page=0&size=10", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Попов"));
    }
}