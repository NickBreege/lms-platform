package org.example.lmsplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ScheduleDtoResponse {
    private Long id;

    private Long studentGroupId;
    private String studentGroupName;

    private Long courseId;
    private String courseName;

    private Long teacherId;
    private String teacherFullName;

    private LocalDateTime lessonTime;
}
