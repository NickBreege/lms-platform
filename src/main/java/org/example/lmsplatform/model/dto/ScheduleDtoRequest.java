package org.example.lmsplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ScheduleDtoRequest {
    private Long studentGroupId;
    private Long courseId;
    private LocalDateTime lessonTime;
}
