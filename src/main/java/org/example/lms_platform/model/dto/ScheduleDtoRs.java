package org.example.lms_platform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ScheduleDtoRs {
    private Long id;
    private Long groupId;
    private Long courseId;
    private LocalDateTime lessonDate;
}
