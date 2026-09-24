package com.managementsystem.studymanagesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StudymanagesystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudymanagesystemApplication.class, args);
    }
}