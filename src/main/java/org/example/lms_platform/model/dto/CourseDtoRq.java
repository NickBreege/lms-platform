package org.example.lms_platform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.lms_platform.model.entity.Teacher;

@Data
@AllArgsConstructor
public class CourseDtoRq {
    private String name;
    private String description;
    private Teacher teacher;
}
