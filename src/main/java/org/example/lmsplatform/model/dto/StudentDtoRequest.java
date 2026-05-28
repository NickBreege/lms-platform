package org.example.lmsplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDtoRequest {
    private String name;
    private String surname;
    private Long studentGroupId;
}
