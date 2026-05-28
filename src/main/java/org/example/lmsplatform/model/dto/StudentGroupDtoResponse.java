package org.example.lmsplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class StudentGroupDtoResponse {
    private Long id;
    private String name;
    private Set<Long> courseIds;
    private Set<Long> studentIds;
    private Set<Long> scheduleIds;
}
