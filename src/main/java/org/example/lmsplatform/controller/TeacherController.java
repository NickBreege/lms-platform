package org.example.lmsplatform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lmsplatform.model.dto.TeacherDtoRequest;
import org.example.lmsplatform.model.dto.TeacherDtoResponse;
import org.example.lmsplatform.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "Teachers", description = "Управление учителями")
@RequiredArgsConstructor
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Создать учителя")
    public TeacherDtoResponse createTeacher(@Valid @RequestBody TeacherDtoRequest teacherDtoRequest) {
        return teacherService.createTeacher(teacherDtoRequest);
    }


    @GetMapping("/{teacherId}")
    @Operation(summary = "Найти учителя по ID")
    public TeacherDtoResponse getTeacherById(@PathVariable Long teacherId) {
        return teacherService.getTeacherById(teacherId);
    }

    @PutMapping("/{teacherId}")
    @Operation(summary = "Обновить учителя")
    public TeacherDtoResponse updateTeacher(@PathVariable Long teacherId,
                                            @Valid @RequestBody TeacherDtoRequest teacherDtoRequest) {
        return teacherService.updateTeacher(teacherId, teacherDtoRequest);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{teacherId}")
    @Operation(summary = "Удалить учителя по ID")
    public void deleteTeacher(@PathVariable Long teacherId) {
        teacherService.deleteTeacher(teacherId);
    }
}