package org.example.lmsplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDtoRequest {
    private String name;
    private String description;
    private Long teacherId;
}
