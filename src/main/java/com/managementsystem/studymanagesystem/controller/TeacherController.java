package com.managementsystem.studymanagesystem.controller;

import com.managementsystem.studymanagesystem.dto.TeacherCreateDto;
import com.managementsystem.studymanagesystem.dto.TeacherDto;
import com.managementsystem.studymanagesystem.service.TeacherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
@Tag(name = "Teachers", description = "Управление преподавателями")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<Page<TeacherDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(teacherService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TeacherDto> create(@Valid @RequestBody TeacherCreateDto dto) {
        return ResponseEntity.ok(teacherService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> update(@PathVariable Long id, @Valid @RequestBody TeacherCreateDto dto) {
        return ResponseEntity.ok(teacherService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}