package org.example.lmsplatform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lmsplatform.model.dto.StudentGroupDtoRequest;
import org.example.lmsplatform.model.dto.StudentGroupDtoResponse;
import org.example.lmsplatform.service.GroupService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "Groups", description = "Управление группами студентов")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Создать группу")
    public StudentGroupDtoResponse createGroup(@Valid @RequestBody StudentGroupDtoRequest groupDtoRequest) {
        return groupService.createGroup(groupDtoRequest);
    }

    @PutMapping("/{groupId}")
    @Operation(summary = "Обновить группу")
    public StudentGroupDtoResponse updateGroup(@PathVariable Long groupId,
                                               @Valid @RequestBody StudentGroupDtoRequest groupDtoRequest) {

        return groupService.updateGroup(groupId, groupDtoRequest);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Удалить группу")
    public void deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
    }

    @PostMapping("/{groupId}/courses/{courseId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Добавить группу на курс")
    public void addGroupToCourse(@PathVariable Long groupId,
                                 @PathVariable Long courseId) {
        groupService.addGroupToCourse(groupId, courseId);
    }
}