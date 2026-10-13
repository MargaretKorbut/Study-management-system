package com.managementsystem.studymanagesystem.controller;

import com.managementsystem.studymanagesystem.repository.CourseRepository;
import com.managementsystem.studymanagesystem.repository.GroupRepository;
import com.managementsystem.studymanagesystem.repository.ScheduleRepository;
import com.managementsystem.studymanagesystem.repository.StudentRepository;
import com.managementsystem.studymanagesystem.repository.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:tc:postgresql:16:///studydb",
                "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
                "spring.datasource.username=test",
                "spring.datasource.password=test",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        })
abstract class AbstractIntegrationTest {

    @Value("${local.server.port}")
    protected int port;

    protected final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    protected TeacherRepository teacherRepository;
    @Autowired
    protected CourseRepository courseRepository;
    @Autowired
    protected GroupRepository groupRepository;
    @Autowired
    protected StudentRepository studentRepository;
    @Autowired
    protected ScheduleRepository scheduleRepository;

    protected String url(String path) {
        return "http://localhost:" + port + "/api/v1" + path;
    }

    @AfterEach
    void cleanDatabase() {
        scheduleRepository.deleteAll();
        courseRepository.deleteAll();
        groupRepository.deleteAll();
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
    }
}