package org.example.lmsplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDtoResponse {
    private Long id;
    private String name;
    private String surname;
    private Long studentGroupId;
    private String studentGroupName;
}
