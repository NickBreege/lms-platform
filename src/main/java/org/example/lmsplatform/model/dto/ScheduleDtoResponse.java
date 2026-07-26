package org.example.lmsplatform.model.dto;

import java.time.LocalDateTime;


public record ScheduleDtoResponse(
        Long id,
        Long studentGroupId,
        String studentGroupName,
        Long courseId,
        String courseName,
        Long teacherId,
        String teacherFullName,
        LocalDateTime lessonStart,
        LocalDateTime lessonEnd
) {
}
