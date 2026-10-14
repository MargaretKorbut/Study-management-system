package com.managementsystem.studymanagesystem.controller;

import com.managementsystem.studymanagesystem.dto.GroupCreateDto;
import com.managementsystem.studymanagesystem.dto.GroupDto;
import com.managementsystem.studymanagesystem.service.GroupService;
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
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@Tag(name = "Groups", description = "Управление группами")
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<Page<GroupDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(groupService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getById(id));
    }

    @PostMapping
    public ResponseEntity<GroupDto> create(@Valid @RequestBody GroupCreateDto dto) {
        return ResponseEntity.ok(groupService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> update(@PathVariable Long id, @Valid @RequestBody GroupCreateDto dto) {
        return ResponseEntity.ok(groupService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{groupId}/students/{studentId}")
    public ResponseEntity<GroupDto> addStudent(@PathVariable Long groupId, @PathVariable Long studentId) {
        return ResponseEntity.ok(groupService.addStudent(groupId, studentId));
    }
}