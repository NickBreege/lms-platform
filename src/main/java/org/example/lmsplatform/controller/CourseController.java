package org.example.lmsplatform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lmsplatform.model.dto.CourseDtoRequest;
import org.example.lmsplatform.model.dto.CourseDtoResponse;
import org.example.lmsplatform.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "Courses", description = "Управление курсами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Создать курс")
    public CourseDtoResponse createCourse(@Valid @RequestBody CourseDtoRequest courseDtoRequest) {
        return courseService.createCourse(courseDtoRequest);
    }

    @PutMapping("/{courseId}")
    @Operation(summary = "Обновить курс")
    public CourseDtoResponse updateCourse(@PathVariable Long courseId,
                                          @Valid @RequestBody CourseDtoRequest courseDtoRequest) {
        return courseService.updateCourse(courseId, courseDtoRequest);
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Удалить курс")
    public void deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "Получить курс")
    public CourseDtoResponse getCourse(@PathVariable Long courseId) {
        return courseService.getCourse(courseId);
    }

    @GetMapping
    @Operation(summary = "Получить список курсов")
    public Page<CourseDtoResponse> getAllCourses(Pageable pageable) {
        return courseService.getAllCourses(pageable);
    }

    @PostMapping("/{courseId}/groups/{groupId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Добавить группу на курс")
    public void addGroupToCourse(@PathVariable Long courseId,
                                 @PathVariable Long groupId) {
        courseService.addGroupToCourse(courseId, groupId);
    }

    @DeleteMapping("/{courseId}/groups/{groupId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Удалить группу с курса")
    public void removeGroupFromCourse(@PathVariable Long courseId,
                                      @PathVariable Long groupId) {
        courseService.removeGroupFromCourse(courseId, groupId);
    }
}