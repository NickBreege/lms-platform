package org.example.lmsplatform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lmsplatform.model.dto.StudentDtoRequest;
import org.example.lmsplatform.model.dto.StudentDtoResponse;
import org.example.lmsplatform.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "Students", description = "Управление студентами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Создать студента")
    public StudentDtoResponse createStudent(@Valid @RequestBody StudentDtoRequest studentDtoRequest) {
        return studentService.createStudent(studentDtoRequest);
    }

    @GetMapping("/{studentId}")
    @Operation(summary = "Показать студента по его ID")
    public StudentDtoResponse getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @PutMapping("/{studentId}")
    @Operation(summary = "Обновить студента")
    public StudentDtoResponse updateStudent(@PathVariable Long studentId,
                                            @Valid @RequestBody StudentDtoRequest studentDtoRequest) {
        return studentService.updateStudent(studentId, studentDtoRequest);
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Удалить студента")
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PostMapping("/{studentId}/groups/{groupId}")
    @Operation(summary = "Назначить студента в группу")
    public StudentDtoResponse assignStudentToGroup(@PathVariable Long studentId,
                                                   @PathVariable Long groupId) {
        return studentService.assignStudentToGroup(studentId, groupId);
    }

    @PostMapping("/groups/{groupId}/assign")
    @Operation(summary = "Назначить студентов в группу")
    public List<StudentDtoResponse> assignStudentsToGroup(@PathVariable Long groupId,
                                                          @RequestBody List<Long> studentIds) {
        return studentService.assignStudentsToGroup(studentIds, groupId);
    }
}