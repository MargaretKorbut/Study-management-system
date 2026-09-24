package com.managementsystem.studymanagesystem.controller;

import com.managementsystem.studymanagesystem.dto.ScheduleCreateDto;
import com.managementsystem.studymanagesystem.dto.ScheduleDto;
import com.managementsystem.studymanagesystem.service.ScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
@Tag(name = "Schedules", description = "Управление расписанием")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ScheduleDto> create(@Valid @RequestBody ScheduleCreateDto dto) {
        return ResponseEntity.ok(scheduleService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> update(@PathVariable Long id, @Valid @RequestBody ScheduleCreateDto dto) {
        return ResponseEntity.ok(scheduleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<Page<ScheduleDto>> getByGroup(@PathVariable Long groupId, Pageable pageable) {
        return ResponseEntity.ok(scheduleService.getByGroup(groupId, pageable));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<Page<ScheduleDto>> getByTeacher(@PathVariable Long teacherId, Pageable pageable) {
        return ResponseEntity.ok(scheduleService.getByTeacher(teacherId, pageable));
    }
}