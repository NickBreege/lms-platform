package org.example.lms_platform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.lms_platform.model.entity.Course;
import org.example.lms_platform.model.entity.Group;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ScheduleDtoRq {
    private Group group;
    private Course course;
    private LocalDateTime lessonTime;
}
