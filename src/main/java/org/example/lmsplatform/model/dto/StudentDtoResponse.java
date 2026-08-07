package org.example.lmsplatform.model.dto;

public record StudentDtoResponse(
        Long id,
        String name,
        String surname,
        Long studentGroupId,
        String studentGroupName
) {
}
