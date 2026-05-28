package org.example.lmsplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class TeacherDtoResponse {
    private Long id;
    private String name;
    private String surname;
    private Set<Long> courseIds;
}
