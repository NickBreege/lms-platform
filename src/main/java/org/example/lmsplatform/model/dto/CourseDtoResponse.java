package org.example.lmsplatform.model.dto;

public record CourseDtoResponse(
        Long id,
        String name,
        String description,
        Long teacherId,
        String teacherName
) {
}
