package org.example.lmsplatform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lmsplatform.model.dto.ScheduleDtoRequest;
import org.example.lmsplatform.model.dto.ScheduleDtoResponse;
import org.example.lmsplatform.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "Schedules", description = "Управление расписанием")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Создать расписание")
    public ScheduleDtoResponse createSchedule(@Valid @RequestBody ScheduleDtoRequest scheduleDtoRequest) {
        return scheduleService.createSchedule(scheduleDtoRequest);
    }

    @GetMapping("/groups/{groupId}")
    @Operation(summary = "Получить расписания группы")
    public List<ScheduleDtoResponse> getGroupSchedule(@PathVariable Long groupId) {
        return scheduleService.getGroupSchedule(groupId);
    }

    @GetMapping("/teachers/{teacherId}")
    @Operation(summary = "Получить расписания учителя")
    public List<ScheduleDtoResponse> getTeacherSchedule(@PathVariable Long teacherId) {
        return scheduleService.getTeacherSchedule(teacherId);
    }

    @PutMapping("/{scheduleId}")
    @Operation(summary = "Обновить расписание")
    public ScheduleDtoResponse updateSchedule(@PathVariable Long scheduleId,
                                              @Valid @RequestBody ScheduleDtoRequest scheduleDtoRequest) {
        return scheduleService.updateSchedule(scheduleId, scheduleDtoRequest);
    }

    @DeleteMapping("/{scheduleId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Удалить расписание")
    public void deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }
}