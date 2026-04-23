package org.example.lms_platform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDtoRq {
    private String name;
    private String surname;
    private Long groupId;
}
